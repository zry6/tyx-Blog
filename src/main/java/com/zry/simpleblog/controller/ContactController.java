package com.zry.simpleblog.controller;

import com.zry.simpleblog.entity.Blog;
import com.zry.simpleblog.entity.Comment;
import com.zry.simpleblog.entity.User;
import com.zry.simpleblog.service.*;
import com.zry.simpleblog.util.MyEmailUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author zry
 * @ClassName contactController.java
 * @Description TODO
 * @createTime 2021年09月15日
 */
@Controller
public class ContactController {
    @Resource
    private CommentService commentService;
    @Resource
    private BlogService blogService;
    @Resource
    private TypeService typeService;
    @Resource
    private TagService tagService;
    @Resource
    private EmailService emailService;
    @GetMapping("/guestbook")
    public String comments(Model model){
        Blog blog = blogService.getBlogByTitle("留言板");
        model.addAttribute("blog",blog);
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(8));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(7));
        return "contact";
    }

    @GetMapping("/contacts/{id}")
    public String contact( @PathVariable Long id,Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(id));
        return "contact :: commentList";
    }

    @PostMapping("/contacts")
    public String post(Comment comment, HttpSession session){
        User user = (User) session.getAttribute("User");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            if(comment.getAvatar().isEmpty()||comment.getAvatar()==null){
                comment.setAvatar("/images/avatar/avatar1.png");
            }
            comment.setAdminComment(false);
        }
        commentService.saveComment(comment);
        MyEmailUtils.commentSendEmail(comment, commentService, blogService, emailService);
        return "redirect:/contacts/"+comment.getBlog().getId();
    }

}
