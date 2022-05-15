package com.zry.simpleBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zry.simpleBlog.comment.exception.BusinessException;
import com.zry.simpleBlog.comment.respBean.RespBeanEnum;
import com.zry.simpleBlog.comment.utils.UserContext;
import com.zry.simpleBlog.dto.CommentDto;
import com.zry.simpleBlog.entity.Blog;
import com.zry.simpleBlog.entity.Comment;
import com.zry.simpleBlog.entity.User;
import com.zry.simpleBlog.mapper.BlogMapper;
import com.zry.simpleBlog.mapper.CommentMapper;
import com.zry.simpleBlog.service.ICommentService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Value("${comment.default.avatar}")
    String avatar;


    /**
     * 功能描述: 存放迭代找出的所有子代的临时列表  ThreadLocal规避 全局变量的并发问题
     *
     * @create 2022/5/12
     */
    private static final ThreadLocal<List<CommentDto>> COMMENT_TEMP_CONTENT = new ThreadLocal<>();


    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommentDto saveComment(CommentDto comment) {
        User user = UserContext.getCurrentUser();
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAdminComment(false);
        }

        if (StringUtils.isEmpty(comment.getAvatar())) {
            comment.setAvatar(avatar);
        }

        comment.setCreateTime(new Date());
        Blog blog = blogMapper.selectById(comment.getBlogId());
        if (blog == null) {
            throw new BusinessException(RespBeanEnum.BLOG_NOT_EXISTED);
        }

        if (comment.getParentComment() != null && !StringUtils.isEmpty(comment.getParentComment().getId())) {
            Comment comment1 = commentMapper.selectById(comment.getParentComment().getId());
            if (comment1 == null) {
                throw new BusinessException(RespBeanEnum.COMMENT_NOT_EXISTED);
            }
        }
        int i = commentMapper.insert(comment.caseToComment());
        if (i != 1) {
            throw new BusinessException(RespBeanEnum.POST_COMMENT_ERROR);
        }
        return comment;
    }

    @Override
    public boolean removeComment(Long id) {
        List<Comment> commentList = commentMapper.selectList(new QueryWrapper<Comment>().eq("parent_comment_id", id));
        if (commentList != null && !commentList.isEmpty()) {
            return false;
        }
        int i = commentMapper.deleteById(id);
        if (i != 1) {
            return false;
        }
        return true;
    }

    @Override
    public Page<CommentDto> pageCommentByBlogId(Integer page, Integer pageSize, Long blogId) {
        //找出所有的一级评论
        Page<Comment> commentsPage = commentMapper.selectPage(new Page<>(page, pageSize), new QueryWrapper<Comment>().eq("blog_id", blogId).isNull("parent_comment_id").orderByDesc("create_time"));
        Page<CommentDto> commentDtoPage = (Page<CommentDto>) commentsPage.convert(this::apply);

        COMMENT_TEMP_CONTENT.set(new ArrayList<>());
        combineChildren(commentDtoPage);
        COMMENT_TEMP_CONTENT.remove();

        return commentDtoPage;
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
     * 功能描述: 以为二级评论还会有回复者所有我们要，递归的查找所有评论子级的回复者
     *
     * @create 2022/5/12
     */
    private void recursively(@NotNull CommentDto comment) {
        //查出父级注入父级
//        comment.setParentComment(apply(commentMapper.selectById(comment.getParentComment().getId())));
        comment.setReplyComments(commentMapper.selectReplyList(comment.getId()));
        //顶节点添加到临时存放集合
        COMMENT_TEMP_CONTENT.get().add(comment);
        if (comment.getReplyComments().size() > 0) {
            List<CommentDto> replies = comment.getReplyComments();
            for (CommentDto reply : replies) {
                // 查出父级 注入父级
//                reply.setParentComment(apply(commentMapper.selectById(comment.getParentComment().getId())));
                reply.setReplyComments(commentMapper.selectReplyList(reply.getId()));
                COMMENT_TEMP_CONTENT.get().add(reply);
                if (reply.getReplyComments().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }

}
