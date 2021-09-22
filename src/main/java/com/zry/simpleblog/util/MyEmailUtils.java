package com.zry.simpleblog.util;

import com.zry.simpleblog.entity.Blog;
import com.zry.simpleblog.entity.Comment;
import com.zry.simpleblog.entity.Email;
import com.zry.simpleblog.service.BlogService;
import com.zry.simpleblog.service.CommentService;
import com.zry.simpleblog.service.EmailService;

/**
 * @author zry
 * @ClassName EmailUtils.java
 * @Description 自定义邮箱模块
 * @createTime 2021年09月21日
 */
public class MyEmailUtils {
    public static void commentSendEmail(Comment comment, CommentService commentService, BlogService blogService, EmailService emailService) {
        Blog blog = blogService.getBlog(comment.getBlog().getId());
        Email email = new Email();
        email.setFrom("1447051936@qq.com");
        if(null == comment.getParentComment()){
            email.setTo(blog.getUser().getEmail());
        }else {
            email.setTo(comment.getParentComment().getEmail());
        }

        email.setSubject("猫喵喵博客回复提示在文章【"+blog.getTitle()+"】中【"+comment.getNickname()+"】评论了您快去看看吧~");
        email.setText(comment.getContent()+"---------------【Ta的邮箱:"+comment.getEmail()+"】文章链接url：http://1.117.229.251/Blog/blog/"+comment.getBlog().getId());
        emailService.sendMail(email);
    }
}
