package com.zry.simpleBlog.comment.aop.annotations;

import java.lang.annotation.*;

/**
 * 功能描述: controller层针对接口的权限验证，这里暂时判断是否登录
 * 会将登录用户信息存储在UserContext中。
 * 注解属性isLogin=true
 * 没有登陆则抛出 BusinessException(RespBeanEnum.AUTH_ERROR)异常
 * 注解属性isLogin=false 就不会抛出异常
 *
 * @author zry
 * @create 2022/4/10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckLogin {
    /**
     * 为true时，用户不登录则抛出异常。
     */
    boolean isLogin() default true;
}
