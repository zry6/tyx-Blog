package com.zry.simpleBlog.comment.interceptor;

import com.zry.simpleBlog.comment.utils.CookieUtil;
import com.zry.simpleBlog.entity.User;
import com.zry.simpleBlog.service.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截静态页面请求
 * 后台管理器
 * @author zry
 * @create 2022-04-10 15:39
 */

public class AdminInterceptor implements HandlerInterceptor {

    @Resource
    private RedisService redisService;

    @Value("${cookie.user.key}")
    private  String cookieKey ;

    @Value("${page.path.login}")
    private  String loginPath ;
    /**
     * 功能描述: 只需要拦截 admin 下的.html静态页面请求，如果是其他 api请求需要登录登录权限 请求的话使用 CheckLogin注解验证
     * 如果登录那么就放行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringBuffer url = request.getRequestURL();
        //从cookie中获取用户 ticket
        String ticket = CookieUtil.getCookieValue(request, cookieKey);
        if (StringUtils.isEmpty(ticket)) {
            response.sendRedirect(loginPath);
            return false;
        }
        //获取用户信息 登陆状态
        User user = redisService.getUserByTicket(request, response,ticket);
        if (user == null) {
            response.sendRedirect(loginPath);

            return false;
        }
        return true;

    }
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
//    }
}
