package com.zry.simpleblog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * 使用 @ControllerAdvice 注释来管理应用程序中的异常。
 * @ControllerAdvice是@Component 注解的一种特殊化，它允许在一个全局处理组件中处理整个应用程序中的异常。
 * 它可以被视为一个拦截器，拦截由注释@RequestMapping和类似的方法抛出的异常。
 * @author zry
 * @ClassName ControllerExceptionHandler.java
 * @Description TODO
 * @createTime 2021年08月28日
 */
@ControllerAdvice
public class ControllerExceptionHandler extends Exception {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = {Exception.class})
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        ModelAndView mv = new ModelAndView();
        logger.error("Request URl : {}, Exception : {}",request.getRequestURI(),e);

//        如果定制了http状态码那么抛出异常
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        mv.addObject("url", request.getRequestURI());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
    }
}
