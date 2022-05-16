package com.zry.simpleBlog.service;

import com.zry.simpleBlog.dto.EmailDto;

/**
 * 邮箱服务
 * @author 14470
 */
public interface EmailService {
    void sendCommentEmail(EmailDto email) ;
}
