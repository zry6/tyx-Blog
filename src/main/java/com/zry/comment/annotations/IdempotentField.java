package com.zry.comment.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 幂等字段注解
 * 用于标注dto中的字段
 * 要配合@Idempotent使用
 * @author zry
 * @create 2022-04-27 9:36
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdempotentField {

}