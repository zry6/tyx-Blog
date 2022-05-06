package com.zry.simpleBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zry.simpleBlog.comment.aop.exception.GlobalException;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.comment.respBean.RespBeanEnum;
import com.zry.simpleBlog.dto.LoginDto;
import com.zry.simpleBlog.entity.User;
import com.zry.simpleBlog.mapper.UserMapper;
import com.zry.simpleBlog.service.IUserService;
import com.zry.simpleBlog.service.RedisService;
import com.zry.simpleBlog.comment.utils.CookieUtil;
import com.zry.simpleBlog.comment.utils.MD5Util;
import com.zry.simpleBlog.comment.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisService redisService;

    @Value("${cookie.user.key}")
    private String cookieKey;
    @Value("${cookie.user.surviveTime}")
    private long surviveTime;

    @Override
    public RespBean doLogin(LoginDto loginDto) {
        if (loginDto == null) {
            throw new GlobalException(RespBeanEnum.ERROR);
        }
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        //先从redis中查询 用户是否登录？

        //从数据库查询用户
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        // 1.判断用户是否存在 或者 判断再次MD5加密的密码是否相等
        if (user == null || !MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        user.setPassword("你不知道");
        user.setSalt("你也不知道");

        //生成 uuid 当作 用户 ticket
        String ticket = UUIDUtil.uuid();
        //将用户信息 存到redis中
        redisService.saveUser(ticket, user, surviveTime);

        //将ticket存入 cookie中
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            throw new GlobalException(RespBeanEnum.SERVICE_ERROR);
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        CookieUtil.setCookie(request, response, cookieKey, ticket);
        //返回 success;
        return RespBean.success(ticket);
    }

//
//    @Override
//    public void updateUser(Integer id, UserVo userVo) {
//        if (userVo == null) {
//            throw new GlobalException(RespBeanEnum.DATA_ERROR);
//        }
//        if (StringUtils.isEmpty(userVo.getUsername())) {
//            userVo.setUsername(null);
//        }
//        if (StringUtils.isEmpty(userVo.getEmail())) {
//            userVo.setEmail(null);
//        }
//        if (StringUtils.isEmpty(userVo.getNickname())) {
//            userVo.setNickname(null);
//        }
//        User user = userVo.castUser();
//        //i为1完成更新一个用户
//        userMapper.update(user, new QueryWrapper<User>().eq("id", id));
//    }


}
