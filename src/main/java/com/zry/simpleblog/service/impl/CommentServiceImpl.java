package com.zry.simpleblog.service.impl;

import com.zry.simpleblog.dao.CommentDao;
import com.zry.simpleblog.entity.Comment;
import com.zry.simpleblog.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zry
 * @ClassName CommentServiceImpl.java
 * @Description TODO
 * @createTime 2021年09月04日
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentDao commentDao;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        List<Comment> comments = commentDao.findByBlogIdAndParentCommentNull(blogId);
        combineChildren(comments);
        return comments;
//        return eachComment(comments);
    }

    @Transactional
    @Override
    public Boolean saveComment(Comment comment) {
        Long parentId = comment.getParentComment().getId();
        if (parentId == -1) {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentDao.save(comment);
    }
    @Override
    public int deleteComment(Long BlogId) {
        return commentDao.deleteComment(BlogId);
    }

    /**
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments) {
            comment.setReplyComments( commentDao.getReplyComments(comment.getId()));
            List<Comment> replys1 = comment.getReplyComments();
            for (Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     *
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        comment.setParentComment(commentDao.findParentById(comment.getParentComment().getId()));
        comment.setReplyComments( commentDao.getReplyComments(comment.getId()));
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size() > 0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                reply.setParentComment(commentDao.findParentById(reply.getParentComment().getId()));
                reply.setReplyComments(commentDao.getReplyComments(reply.getId()));
                tempReplys.add(reply);
                if (reply.getReplyComments().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }
}
//    /**
//     * 循环每个顶级的评论节点   就是复制一遍
//     *
//     * @param comments
//     * @return
//     */
//    private List<Comment> eachComment(List<Comment> comments) {
//        List<Comment> commentsView = new ArrayList<>();
//        for (Comment comment : comments) {
//            Comment c = new Comment();
//            BeanUtils.copyProperties(comment, c);
//            commentsView.add(c);
//        }
//        //合并评论的各层子代到第一级子代集合中
//        combineChildren(commentsView);
//        return commentsView;
//    }
