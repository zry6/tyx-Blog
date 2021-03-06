package com.zry.simpleBlog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zry.simpleBlog.comment.exception.BusinessException;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.comment.enums.RespBeanEnum;
import com.zry.simpleBlog.comment.utils.CookieUtil;
import com.zry.simpleBlog.comment.utils.MD5Util;
import com.zry.simpleBlog.comment.utils.UUIDUtil;
import com.zry.simpleBlog.comment.utils.UserContext;
import com.zry.simpleBlog.dto.LoginDto;
import com.zry.simpleBlog.dto.UpdatePswDto;
import com.zry.simpleBlog.entity.User;
import com.zry.simpleBlog.mapper.UserMapper;
import com.zry.simpleBlog.service.IUserService;
import com.zry.simpleBlog.service.RedisService;
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
            throw new BusinessException(RespBeanEnum.ERROR);
        }

        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        //先从redis中查询 用户是否登录？

        //从数据库查询用户
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));

        // 1.判断用户是否存在 或者 判断再次MD5加密的密码是否相等
        if (user == null || !MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
            throw new BusinessException(RespBeanEnum.LOGIN_ERROR);
        }
        user.setPassword("你不知道");
        user.setSalt("你也不知道");

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            throw new BusinessException(RespBeanEnum.SERVICE_ERROR);
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();

        //看看是否已经登录，如果已经登录，就直接返回
        String oldTicket = CookieUtil.getCookieValue(request, cookieKey);
        User userByTicket = redisService.getUserByTicket(request, response, oldTicket);
        if (userByTicket != null && userByTicket.getId().equals(user.getId())) {
            return RespBean.success(oldTicket);
        }
        //生成 uuid 当作 用户 ticket
        String ticket = UUIDUtil.uuid();

        //将用户信息 存到redis中

        redisService.saveUser(ticket, user, surviveTime);

        //将ticket存入 cookie中
        CookieUtil.setCookie(request, response, cookieKey, ticket);
        //返回 success;
        return RespBean.success(ticket);
    }


    @Override
    public RespBean updatePassword(UpdatePswDto pswDto, HttpServletRequest request) {
        String password = pswDto.getPassword();
        if (!password.equals(pswDto.getCheckPassword())) {
            throw new BusinessException("两次输入密码不一致");
        }
        String userTicket = CookieUtil.getCookieValue(request, cookieKey);

        User currentUser = UserContext.getCurrentUser();
        User user = new User();


        user.setId(currentUser.getId());
        User user1 = userMapper.selectOne(new QueryWrapper<User>().select("salt").eq("id", user.getId()));
        user.setPassword(MD5Util.inputPassToDBPass(password, user1.getSalt()));

        int result = userMapper.updateById(user);
        if (result == 1) {
            redisService.deleteUser(userTicket);
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.PASSWORD_UPDATE_FAIL);
    }


}
