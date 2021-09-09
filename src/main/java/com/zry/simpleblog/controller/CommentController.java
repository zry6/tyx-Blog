package com.zry.simpleblog.controller;

import com.zry.simpleblog.entity.Comment;
import com.zry.simpleblog.entity.User;
import com.zry.simpleblog.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author zry
 * @ClassName CommentController.java
 * @Description TODO
 * @createTime 2021年09月06日
 */
@Controller
public class CommentController {
    @Resource
    private CommentService commentService;
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        String avatar = "/images/avatar/avatar1.png";
        User user = (User) session.getAttribute("User");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            if(comment.getAvatar().isEmpty()||comment.getAvatar()==null){
                comment.setAvatar(avatar);
            }
            comment.setAdminComment(false);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/"+comment.getBlog().getId();
    }

}
