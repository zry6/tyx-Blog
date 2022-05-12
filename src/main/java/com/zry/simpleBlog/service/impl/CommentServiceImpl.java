package com.zry.simpleBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
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

    @Override
    public List<CommentDto> listCommentByBlogId(Integer page, Integer pageSize, Long blogId) {
        //找出所有的一级评论
        IPage<Comment> commentsPage = commentMapper.selectPage(new Page<>(page, pageSize), new QueryWrapper<Comment>().eq("blog_id", blogId).isNull("parent_comment_id").orderByAsc("create_time"));
        IPage<CommentDto> commentDtoPage = commentsPage.convert(this::apply);
        combineChildren(commentDtoPage);
        return null;
    }

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
            if (comment1==null){
                throw new BusinessException(RespBeanEnum.COMMENT_NOT_EXISTED);
            }
        }
        int i = commentMapper.insert(comment.caseToComment());
        if (i != 1) {
            throw new BusinessException(RespBeanEnum.POST_COMMENT_ERROR);
        }
        return comment;
    }

    private void combineChildren(IPage<CommentDto> comments) {
        List<CommentDto> records = comments.getRecords();

        for (CommentDto comment : records) {
            //获取所有的二级消息
            List<CommentDto> replyComments = commentMapper.selectReplyList(comment.getId());

            comment.setReplyComments(replyComments);

            List<CommentDto> replies = comment.getReplyComments();
            for (CommentDto reply : replies) {
                //循环迭代，找出子代，存放在tempReplays中
                recursively(reply);
            }
            log.debug(replies.toString());
            //修改顶级节点的reply集合为迭代处理后的列表
            comment.setReplyComments(tempReplies);
            //清除临时存放区
            tempReplies.clear();
        }
    }

    /**
     * 功能描述:存放迭代找出的所有子代的临时列表
     *
     * @create 2022/5/12
     */
    private final List<CommentDto> tempReplies = new ArrayList<>();

    /**
     * 功能描述: 以为二级评论还会有回复者所有我们要，递归的查找所有评论子级的回复者
     *
     * @create 2022/5/12
     */
    private void recursively(CommentDto comment) {
//        comment.setParentComment(commentMapper.selectById(comment.));
//        comment.setReplyComments( commentMapper.getReplyComments(comment.getId()));
//        //顶节点添加到临时存放集合
//        tempReplies.add(comment);
//        if (comment.getReplyComments().size() > 0) {
//            List<CommentDto> rplies = comment.getReplyComments();
//
//            for (CommentDto reply : rplies) {
//                reply.setParentComment(commentMapper.findParentById(reply.getParentComment().getId()));
//                reply.setReplyComments(commentMapper.getReplyComments(reply.getId()));
//                tempReplies.add(reply);
//                if (reply.getReplyComments().size() > 0) {
//                    recursively(reply);
//                }
//            }
//        }
    }

    private CommentDto apply(Comment u) {
        CommentDto v = new CommentDto();
        BeanUtils.copyProperties(u, v);
        return v;
    }
}
