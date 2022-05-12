package com.zry.simpleBlog.controller;


import com.zry.simpleBlog.comment.aop.annotations.CheckLogin;
import com.zry.simpleBlog.comment.aop.annotations.Idempotent;
import com.zry.simpleBlog.comment.aop.annotations.IdempotentStrategy;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.dto.CommentDto;
import com.zry.simpleBlog.service.ICommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
@Api(tags = "评论管理接口")
@RestController
public class CommentController {
    @Resource
    private ICommentService commentService;


    @GetMapping("/comments/{blogId}")
    public RespBean comments(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize, @PathVariable Long blogId) {
        List<CommentDto> comments = commentService.listCommentByBlogId(page, pageSize, blogId);
        return RespBean.success(comments);
    }

    @ApiOperation(value = "发表评论", notes = "实现幂等性")
    @Idempotent(timeout = 5, strategy = IdempotentStrategy.IDEMPOTENT_INTERFACE)
    @CheckLogin(isLogin = false)
    @PostMapping("/comments")
    public RespBean post(@RequestBody @Valid CommentDto comment) {
        CommentDto commentDto = commentService.saveComment(comment);
        return RespBean.success(commentDto);
    }

}
