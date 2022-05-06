package com.zry.simpleBlog.comment.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 幂等注解：用于controller层方法
 *
 * @author zry
 * @create 2022-04-26 21:30
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * @return key超时时间；默认10秒超时
     */
    long timeout() default 10;

    /**
     * @return 幂等策略
     */
    IdempotentStrategy strategy();
}
