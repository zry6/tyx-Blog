package com.zry.simpleBlog.comment.aop.annotations;

import com.zry.simpleBlog.comment.enums.AuthRankEnum;

import java.lang.annotation.*;

/**
 * 功能描述:
 * isLogin = true：只有登录之后才能进入该方法,并将登录的UserInfo注入UserContext
 * isLogin = false：一切校验将不生效
 *
 * @author zry
 * @create 2022/4/10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthCheck {
    /**
     * 为true时，用户不登录则抛出异常。
     */
    boolean isLogin() default true;

    /**
     * 需要校验的角色标识
     * @return 需要校验的角色标识
     */
    String [] value() default {};

    /**
     * 定义该操作所需用户最低的权限等级
     * @return AuthRankEnum
     */
    AuthRankEnum rank() default AuthRankEnum.VISITOR;
}
