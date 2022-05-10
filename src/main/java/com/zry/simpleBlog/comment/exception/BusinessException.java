package com.zry.simpleBlog.comment.exception;

import com.zry.simpleBlog.comment.respBean.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义全局异常 BusinessException
 *
 * @author zry
 * @ClassName BusinessException.java
 * @createTime 2022年01月01日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    /**
     * 异常编号
     */
    private int messageCode;
    /**
     * 对messageCode 异常信息进行补充说明
     */
    private String detailMessage;

    public BusinessException(String message) {
            super(message);
            this.messageCode = RespBeanEnum.ERROR.getCode();
            this.detailMessage = message;
    }

    public BusinessException(RespBeanEnum code) {
        this(code.getCode(), code.getMessage());
    }

}
