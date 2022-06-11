package com.zry.simpleBlog.controller;


import com.zry.simpleBlog.comment.aop.annotations.AuthCheck;
import com.zry.simpleBlog.comment.aop.annotations.Idempotent;
import com.zry.simpleBlog.comment.enums.AuthRankEnum;
import com.zry.simpleBlog.comment.enums.IdempotentStrategyEnum;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.dto.CommentDto;
import com.zry.simpleBlog.service.ICommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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


    @GetMapping("comments/{blogId}")
    public RespBean comments(@RequestParam(defaultValue = "-1") Integer page, @RequestParam(defaultValue = "-1") Integer pageSize, @PathVariable Long blogId) {
        return commentService.pageCommentByBlogId(page, pageSize, blogId);
    }

    @ApiOperation(value = "发表评论", notes = "实现幂等性校验")
    @Idempotent(timeout = 5, strategy = IdempotentStrategyEnum.IDEMPOTENT_INTERFACE)
    @AuthCheck(isLogin = false)
    @PostMapping("comments")
    public RespBean post(@RequestBody @Valid CommentDto comment) {
        return commentService.saveComment(comment);
    }

    @ApiOperation(value = "删除评论")
    @AuthCheck(rank = AuthRankEnum.GOD)
    @DeleteMapping("comments/{id}")
    public RespBean delete(@PathVariable Long id) {
        return commentService.removeComment(id);
    }
}
