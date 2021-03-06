package com.zry.simpleBlog.comment.aop.annotations;

import java.lang.annotation.*;

/**
 * LogWeb
 *
 * @author zry
 * @version V2.0
 * @date 2022年4月10日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogWeb {
    /**
     * 所属模块
     */
    String title() default "";

    /**
     * 功能
     */
    String action() default "";
}
