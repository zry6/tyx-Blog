package com.zry.simpleBlog.controller.admin;


import com.zry.simpleBlog.comment.annotations.CheckLogin;
import com.zry.simpleBlog.comment.annotations.LogAnnotation;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.comment.respBean.RespBeanEnum;
import com.zry.simpleBlog.dto.LoginDto;
import com.zry.simpleBlog.dto.UserDto;
import com.zry.simpleBlog.entity.User;
import com.zry.simpleBlog.service.IUserService;
import com.zry.simpleBlog.service.RedisService;
import com.zry.simpleBlog.comment.utils.CookieUtil;
import com.zry.simpleBlog.comment.utils.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author zry
 * @since 2022-04-09
 */
@Api(tags = "用户信息接口")
@RestController
public class AdminUserController {
    @Resource
    private IUserService userService;

    @Resource
    private RedisService redisService;

    @Value("${cookie.user.key}")
    private String cookieKey;

    /**
     * 功能描述: 登录
     *
     * @author zry
     */
    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    @LogAnnotation(title = "后台管理", action = "用户登录")
    public RespBean login(@RequestBody @Valid LoginDto loginDto) {
        return userService.doLogin(loginDto);
    }

    /**
     * 功能描述: 通过cookie中的token获取用户信息
     *
     * @author zry
     */
    @ApiOperation(value = "获取已登录用户信息")
    @GetMapping("userInfoByTicket")
    @CheckLogin
    @LogAnnotation(title = "用户操作", action = "ThreadLocal或redis中获取用户信息")
    public RespBean userInfo() {
        User user = UserContext.getCurrentUser();
        return RespBean.success(new UserDto(user));
    }

    /**
     * 功能描述: 更新用户信息
     *
     * @author zry
     */
    @ApiOperation(value = "更新用户信息", notes = "暂时不包括密码")
    @PutMapping("user/{id}")
    @CheckLogin
    @LogAnnotation(title = "用户操作", action = "更新用户信息")
    public RespBean updateUserInfo(@Valid @RequestBody UserDto userDto, @PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) {
        //更新数据库中的用户信息
        userDto.setId(Long.valueOf(id));
        userService.updateById(userDto.castUser());
        String ticket = CookieUtil.getCookieValue(request, cookieKey);
        //根据ticket删除redis中的用户信息 ，让用户重新登录
        redisService.deleteUser(ticket);
        CookieUtil.deleteCookie(request,response,cookieKey);
        return RespBean.success(RespBeanEnum.UPDATE_SUCCESS);
    }


    /**
     * 功能描述: 推出登录
     *
     * @author zry
     */
    @ApiOperation(value = "退出登录", notes = "将cookie中的key-value可redis中的用户信息删除")
    @GetMapping("admin/logout")
    @CheckLogin
    @LogAnnotation(title = "用户操作", action = "退出登录")
    public RespBean updateUserInfo(HttpServletRequest request,
                                   HttpServletResponse response) {
        //1.删除redis中的user信息
        redisService.deleteUser(cookieKey);
        //2.删除cookie 中的user Token
        CookieUtil.deleteCookie(request, response, cookieKey);
        return RespBean.success();
    }

}