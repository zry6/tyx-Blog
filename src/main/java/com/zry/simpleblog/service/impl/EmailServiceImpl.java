package com.zry.simpleblog.service.impl;

import com.zry.simpleblog.entity.Email;
import com.zry.simpleblog.service.EmailService;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * @author zry
 * @ClassName EmailServiceImpl.java
 * @Description TODO
 * @createTime 2021年09月21日
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Resource
    private JavaMailSenderImpl mailSender;//注入邮件工具类

    @Async
    @Override
    public Email sendMail(Email email) {
        try{
            sendMineMail(email);
            return email;
        }catch (Exception e){
            email.setStatus("fail");
            email.setError("发送邮箱失败");
            log.println(email.getError()+"msg={}"+e);
            return email;
        }
    }
    private void sendMineMail(Email email) {
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);//true表示支持复杂类型
            messageHelper.setFrom(email.getFrom());//邮件发信人
            messageHelper.setTo(email.getTo().split(","));//邮件收信人
            messageHelper.setSubject(email.getSubject());//邮件主题
            messageHelper.setText(email.getText());//邮件内容

            if (email.getSentDate() == null) {//发送时间
                email.setSentDate(new Date());
                messageHelper.setSentDate(email.getSentDate());
            }
            mailSender.send(messageHelper.getMimeMessage());//正式发送邮件
            email.setStatus("ok");
        } catch (Exception e) {
            throw new RuntimeException(e);//发送失败
        }

    }
}
