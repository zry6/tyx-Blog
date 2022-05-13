package com.zry.simpleBlog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zry.simpleBlog.dto.CommentDto;
import com.zry.simpleBlog.entity.Comment;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
public interface ICommentService extends IService<Comment> {

    Page<CommentDto> pageCommentByBlogId(Integer current, Integer pageSize, Long blogId);

    CommentDto saveComment(CommentDto comment);
}
