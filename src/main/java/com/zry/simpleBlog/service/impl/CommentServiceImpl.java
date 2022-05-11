package com.zry.simpleBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zry.simpleBlog.dto.CommentDto;
import com.zry.simpleBlog.entity.Comment;
import com.zry.simpleBlog.mapper.CommentMapper;
import com.zry.simpleBlog.service.ICommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public List<CommentDto> listCommentByBlogId(Integer page, Integer pageSize, Long blogId) {
        //找出所有的一级评论
        IPage<Comment> commentsPage = commentMapper.selectPage(new Page<>(page, pageSize), new QueryWrapper<Comment>().eq("blog_id", blogId).isNull("parent_comment_id").orderByAsc("create_time"));
        IPage<CommentDto> commentDtoPage = commentsPage.convert(this::apply);
        combineChildren(commentsPage);

        return null;
    }

    private void combineChildren(IPage<Comment> comments) {

    }

    private CommentDto apply(Comment u) {
        CommentDto v = new CommentDto();
        BeanUtils.copyProperties(u, v);
        return v;
    }
}
