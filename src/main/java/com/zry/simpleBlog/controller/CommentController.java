package com.zry.simpleBlog.controller;


import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.dto.CommentDto;
import com.zry.simpleBlog.service.ICommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
@Controller
public class CommentController {
    @Resource
    private ICommentService commentService;

    @GetMapping("/comments/{blogId}")
    public RespBean comments(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize, @PathVariable Long blogId){
        List<CommentDto> comments = commentService.listCommentByBlogId(page,pageSize,blogId);
        return RespBean.success(comments);
    }
}
