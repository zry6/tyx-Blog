package com.zry.comment.annotations;

import java.lang.annotation.*;

/**
 * LogAnnotation
 *
 * @author zry
 * @version V2.0
 * @date 2022年4月10日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    /**
     * 模块
     */
    String title() default "";

    /**
     * 功能
     */
    String action() default "";
}
