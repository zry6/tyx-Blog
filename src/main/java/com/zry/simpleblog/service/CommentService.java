package com.zry.simpleblog.service;

import com.github.pagehelper.PageInfo;
import com.zry.simpleblog.entity.Comment;

import java.util.List;

/**
 * @author zry
 * @ClassName CommentServiceImpl.java
 * @Description TODO
 * @createTime 2021年09月04日
 */
public interface CommentService {
    List<Comment> listCommentByBlogId(Long id);
    PageInfo listCommentByBlogId(int pageNum, int i, Long blogId);
    Boolean saveComment(Comment comment);
    int deleteComment(Long BlogId);
}
