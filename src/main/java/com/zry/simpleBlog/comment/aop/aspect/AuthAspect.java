package com.zry.simpleBlog.comment.aop.aspect;

import com.zry.simpleBlog.comment.aop.annotations.AuthCheck;
import com.zry.simpleBlog.comment.exception.BusinessException;
import com.zry.simpleBlog.comment.enums.RespBeanEnum;
import com.zry.simpleBlog.comment.utils.CookieUtil;
import com.zry.simpleBlog.comment.utils.UserContext;
import com.zry.simpleBlog.entity.User;
import com.zry.simpleBlog.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * CheckLogin注解的切面 暂时做方法上的登陆验证
 * 会将登录用户信息存储在UserContext中。
 * 注解属性isLogin=true
 * 没有登陆则抛出 BusinessException(RespBeanEnum.AUTH_ERROR)异常
 * 注解属性isLogin=false 就不会抛出异常
 *
 * @author zry
 * @create 2022-04-10 17:33
 */
@Slf4j
@Aspect
@Component
public class AuthAspect {

    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;
    @Resource
    private RedisService redisService;

    @Value("${cookie.user.key}")
    private String cookieKey;

    @Pointcut(value = "@annotation(com.zry.simpleBlog.comment.aop.annotations.AuthCheck)")
    public void landerPointCut() {
    }

    @Before("landerPointCut()")
    public void doBefore(JoinPoint point) {
        handleLogin(point);
    }

    private void handleLogin(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        AuthCheck permissions = method.getAnnotation(AuthCheck.class);

        User user = getUser(request, response);
        if (permissions.isLogin()) {
            if (user == null) {
                throw new BusinessException(RespBeanEnum.TOKEN_ERROR);
            }
            if (permissions.rank().getRank() < user.getRank()) {
                throw new BusinessException(RespBeanEnum.AUTH_ERROR);
            }
            log.debug("鉴权通过,用户等级为"+user.getRank());
        }
        UserContext.addCurrentUser(user);
    }

    /**
     * 功能描述: 获取当前登录用户
     *
     * @author zry
     */
    private User getUser(HttpServletRequest request, HttpServletResponse response) {
        String ticket = CookieUtil.getCookieValue(request, cookieKey);
        if (StringUtils.isEmpty(ticket)) {
            return null;
        }
        return redisService.getUserByTicket(request, response, ticket);
    }


    @After(value = "landerPointCut()")
    public void doAfter() {
        UserContext.remove();
    }
}
