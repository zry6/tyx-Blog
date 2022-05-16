package com.zry.simpleBlog.service.impl;

import com.zry.simpleBlog.comment.exception.BusinessException;
import com.zry.simpleBlog.dto.EmailDto;
import com.zry.simpleBlog.service.EmailService;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

@Service
public class EmailServiceImpl implements EmailService {

    @Resource
    JavaMailSenderImpl mailSender;

    /**
     * 评论发送提醒邮件
     *
     */
    @Override
    @Async("mySimpleAsync")
    public void sendCommentEmail(EmailDto email) {
        System.out.println(email);
        mailSender.createMimeMessage();
        MimeMessageHelper helper = null;

        // 设置utf-8或GBK编码，否则邮件会有乱码
        MimeMessage message = mailSender.createMimeMessage();
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setSubject("tyux博客评论提醒");
            helper.setText(
                    "<html><body><p>刚刚有人回复了您</p>" +
                            "回复内容: "
                            + "<p>" + email.getContent() + "</p>"
                            + "博客地址: <a href='"+ email.getUrl() +"'>博客地址</a><body></html>", true)
            ;
            helper.setTo(email.getEmailTo());
            helper.setFrom("1447051936@qq.com");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new BusinessException("邮箱发送失败" + Arrays.toString(e.getStackTrace()));
        }
        mailSender.send(message);
    }
}
