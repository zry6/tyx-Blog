package com.zry.simpleBlog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zry.simpleBlog.comment.aop.annotations.CheckLogin;
import com.zry.simpleBlog.comment.aop.annotations.Idempotent;
import com.zry.simpleBlog.comment.aop.annotations.IdempotentStrategy;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.comment.respBean.RespBeanEnum;
import com.zry.simpleBlog.dto.CommentDto;
import com.zry.simpleBlog.dto.EmailDto;
import com.zry.simpleBlog.service.EmailService;
import com.zry.simpleBlog.service.ICommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @Resource
    private EmailService emailService;

    @GetMapping("comments/{blogId}")
    public RespBean comments(@RequestParam(defaultValue = "-1") Integer page, @RequestParam(defaultValue = "-1") Integer pageSize, @PathVariable Long blogId) {
        Page<CommentDto> comments = commentService.pageCommentByBlogId(page, pageSize, blogId);
        return RespBean.success(comments);
    }

    @ApiOperation(value = "发表评论", notes = "实现了幂等性")
    @Idempotent(timeout = 5, strategy = IdempotentStrategy.IDEMPOTENT_INTERFACE)
    @CheckLogin(isLogin = false)
    @PostMapping("comments")
    public RespBean post(@RequestBody @Valid CommentDto comment,HttpServletRequest request) {
        CommentDto commentDto = commentService.saveComment(comment);

        EmailDto emailDto = new EmailDto();
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

        emailDto.setEmailTo(comment.getParentComment().getEmail());
        emailDto.setUrl(basePath);
        emailDto.setContent(comment.getContent());
        emailService.sendCommentEmail(emailDto);

        return RespBean.success(commentDto);
    }

    @ApiOperation(value = "发送邮件")
    @PostMapping("email")
    public RespBean email(@RequestBody @Valid EmailDto emailDto,HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        emailDto.setUrl(String.valueOf(url));
        emailService.sendCommentEmail(emailDto);
        return RespBean.success();
    }

    @ApiOperation(value = "删除评论")
    @CheckLogin
    @DeleteMapping("comments/{id}")
    public RespBean delete(@PathVariable Long id) {
        boolean b = commentService.removeComment(id);
        if (!b) {
            return RespBean.error(RespBeanEnum.DELETE_ERROR);
        }
        return RespBean.success();
    }
}
