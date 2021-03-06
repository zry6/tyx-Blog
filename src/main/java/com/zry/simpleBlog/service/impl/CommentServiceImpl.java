package com.zry.simpleBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zry.simpleBlog.comment.exception.BusinessException;
import com.zry.simpleBlog.comment.enums.RespBeanEnum;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.comment.utils.UserContext;
import com.zry.simpleBlog.dto.CommentDto;
import com.zry.simpleBlog.dto.EmailDto;
import com.zry.simpleBlog.entity.Blog;
import com.zry.simpleBlog.entity.Comment;
import com.zry.simpleBlog.entity.User;
import com.zry.simpleBlog.mapper.BlogMapper;
import com.zry.simpleBlog.mapper.CommentMapper;
import com.zry.simpleBlog.service.EmailService;
import com.zry.simpleBlog.service.ICommentService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * 评论
 *
 * @author zry
 * @since 2022-04-09
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;
    @Resource
    private BlogMapper blogMapper;

    @Resource
    private EmailService emailService;

    @Value("${comment.default.avatar}")
    private String avatar;
    @Value("${init.article.guestbook.id}")
    private Long guestbookId;

    /**
     * 功能描述: 存放迭代找出的所有子代的临时列表  ThreadLocal规避 全局变量的并发问题
     *
     * @create 2022/5/12
     */
    private static final ThreadLocal<List<CommentDto>> COMMENT_TEMP_CONTENT = new ThreadLocal<>();


    @Transactional(rollbackFor = Exception.class)
    @Override
    public RespBean saveComment(CommentDto comment) {
        User user = UserContext.getCurrentUser();
        comment.setAdminComment(false);
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }

        if (StringUtils.isEmpty(comment.getAvatar())) {
            comment.setAvatar(avatar);
        }

        Blog blog = blogMapper.selectById(comment.getBlogId());
        if (blog == null) {
            throw new BusinessException(RespBeanEnum.BLOG_NOT_EXISTED);
        }

        comment.setCreateTime(new Date());

        if (comment.getParentComment() != null && !StringUtils.isEmpty(comment.getParentComment().getId())) {
            Comment parentComment = commentMapper.selectById(comment.getParentComment().getId());
            if (parentComment == null) {
                throw new BusinessException(RespBeanEnum.COMMENT_NOT_EXISTED);
            }
        }

        int i = commentMapper.insert(comment.caseToComment());
        if (i != 1) {
            throw new BusinessException(RespBeanEnum.POST_COMMENT_ERROR);
        }
        sendEmail(comment, blog);
        return RespBean.success();
    }

    private void sendEmail(CommentDto comment, Blog blog) {
        EmailDto emailDto = new EmailDto();
        emailDto.setBlogTitle(blog.getTitle());
        emailDto.setEmailTo(comment.getParentComment().getEmail());
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        if (blog.getId().equals(guestbookId)) {
            basePath += "p/guestbook.html";
        } else {
            basePath += "p/blog.html?article=" + blog.getId();
        }
        emailDto.setUrl(basePath);
        emailDto.setContent(comment.getContent());

        emailService.sendCommentEmail(emailDto);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RespBean removeComment(Long id) {

        List<Comment> commentList = commentMapper.selectList(new QueryWrapper<Comment>().eq("parent_comment_id", id));
        if (commentList != null && !commentList.isEmpty()) {
            return RespBean.error(RespBeanEnum.DELETE_ERROR);
        }
        int i = commentMapper.deleteById(id);
        if (i != 1) {
            return RespBean.error(RespBeanEnum.DELETE_ERROR);
        }
        return RespBean.success();
    }

    @Override
    public RespBean pageCommentByBlogId(Integer page, Integer pageSize, Long blogId) {
        //找出所有的一级评论
        Page<Comment> commentsPage = commentMapper.selectPage(new Page<>(page, pageSize), new QueryWrapper<Comment>().eq("blog_id", blogId).isNull("parent_comment_id").orderByDesc("create_time"));
        Page<CommentDto> commentDtoPage = (Page<CommentDto>) commentsPage.convert(this::apply);
        COMMENT_TEMP_CONTENT.set(new ArrayList<>());
        combineChildren(commentDtoPage);
        COMMENT_TEMP_CONTENT.remove();
        return RespBean.success(commentDtoPage);
    }


    /**
     * 类型转化
     *
     * @param u
     * @return
     */
    private CommentDto apply(Comment u) {
        log.debug(u.toString());
        CommentDto v = new CommentDto();
        copyProperties(u, v);
        return v;
    }

    private void combineChildren(Page<CommentDto> comments) {
        List<CommentDto> records = comments.getRecords();

        for (CommentDto comment : records) {
            //获取所有的二级消息
            List<CommentDto> replyComments = commentMapper.selectReplyList(comment.getId());

            comment.setReplyComments(replyComments);

            List<CommentDto> replies = comment.getReplyComments();
            for (CommentDto reply : replies) {
                //循环迭代，找出子代的所有子代，存放在tempReplays中
                recursively(reply);
            }
            log.debug(replies.toString());
            //修改顶级节点的reply集合为迭代处理后的列表
            comment.setReplyComments(COMMENT_TEMP_CONTENT.get());
//            //清除临时存放区
            COMMENT_TEMP_CONTENT.set(new ArrayList<>());
        }
    }


    /**
     * 功能描述: 因为二级评论还会有回复者所以我们要递归的查找所有二级评论的回复者
     *
     * @create 2022/5/12
     */
    private void recursively(@NotNull CommentDto comment) {
        // 查出父级注入父级
        comment.setReplyComments(commentMapper.selectReplyList(comment.getId()));
        // 顶节点添加到临时存放集合
        COMMENT_TEMP_CONTENT.get().add(comment);
        if (comment.getReplyComments().size() > 0) {
            List<CommentDto> replies = comment.getReplyComments();
            for (CommentDto reply : replies) {
                // 查出父级 注入父级
                reply.setReplyComments(commentMapper.selectReplyList(reply.getId()));
                COMMENT_TEMP_CONTENT.get().add(reply);
                if (reply.getReplyComments().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }

}
