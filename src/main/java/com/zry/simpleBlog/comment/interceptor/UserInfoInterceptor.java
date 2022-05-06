package com.zry.simpleBlog.comment.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 *
 *
 * @author zry
 * @create 2022-04-10 15:39
 */

public class UserInfoInterceptor implements HandlerInterceptor {
//
//    @Resource
//    private RedisService redisService;
//
//    @Value("${cookie.user.key}")
//    private String cookieKey;
//    /**
//     * 功能描述: 设置用户信息在 userContext
//     * 如果登录那么就放行
//     */
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        //从cookie中获取用户 ticket
//        String ticket = CookieUtil.getCookieValue(request, cookieKey);
//        User user = null;
//        if (!StringUtils.isEmpty(ticket)) {
//            //获取用户信息 登陆状态
//             user = redisService.getUserByTicket(request, response, ticket);
//        }
//        if(user != null){
//            UserContext.addCurrentUser(user);
//        }
//        return true;
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
//        UserContext.remove();
//    }
}
