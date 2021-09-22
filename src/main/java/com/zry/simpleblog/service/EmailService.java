package com.zry.simpleblog.service;

import com.zry.simpleblog.entity.Email;

/**
 * @author zry
 * @ClassName MailService.java
 * @Description TODO
 * @createTime 2021年09月21日
 */
public interface EmailService {
    Email sendMail(Email email);
}
