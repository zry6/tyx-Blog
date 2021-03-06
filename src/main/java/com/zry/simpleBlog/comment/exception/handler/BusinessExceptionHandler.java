package com.zry.simpleBlog.comment.exception.handler;


import com.zry.simpleBlog.comment.exception.BusinessException;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.comment.enums.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


/**
 * 全局异常处理类
 *
 * @author zry
 * @ClassName BusinessExceptionHandler.java
 * @createTime 2022年01月01日
 */
@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

    /**
     * 功能描述: 捕获自定义异常 BusinessException
     *
     * @param
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public RespBean globalExceptionHandler(BusinessException e) {
        return RespBean.error(e.getMessageCode(), e.getDetailMessage());
    }

    /**
     * 参数校验错误异常  就是 Valid 校验抛出的异常
     *
     * @param
     * @return
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public RespBean bindExceptionHandler(Exception e, HttpServletRequest request) {
        BindingResult br = null;
        if (e instanceof BindException) {
            br = ((BindException) e).getBindingResult();
        } else {
            br = ((MethodArgumentNotValidException) e).getBindingResult();
        }
        RespBean respBean = RespBean.error(RespBeanEnum.ERROR);
        respBean.setMsg(br.getAllErrors().get(0).getDefaultMessage());
        return respBean;
    }

    /**
     * 功能描述: 捕获其他异常 Exception
     *
     * @param
     * @return
     */
    @ExceptionHandler(Exception.class)
    public RespBean exceptionHandler(Exception e, HttpServletRequest request) throws ServletException {
        if (e instanceof ServletException) {
            e.printStackTrace();
            throw (ServletException) e;
        }
        log.error("server inner error: {}", e);
        return RespBean.error(RespBeanEnum.SERVICE_ERROR);
    }
}
