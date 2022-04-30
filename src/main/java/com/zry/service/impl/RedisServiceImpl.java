package com.zry.service.impl;

import com.zry.comment.aop.exception.GlobalException;
import com.zry.comment.respBean.RespBeanEnum;
import com.zry.entity.User;
import com.zry.service.RedisService;
import com.zry.comment.utils.CookieUtil;
import com.zry.comment.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zry
 * @create 2022-04-10 9:53
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${cookie.user.key}")
    private String cookieKey;
    @Value("${cookie.user.surviveTime}")
    private long surviveTime;

    @Override
    public void saveUser(String ticket, User user, long surviveTime) {
        String userKey = RedisKeyUtil.getUserKey(ticket);
        set(userKey, user, surviveTime, TimeUnit.HOURS);
    }

    @Override
    public User getUserByTicket(HttpServletRequest request, HttpServletResponse response, String ticket) {

        String userKey = RedisKeyUtil.getUserKey(ticket);
        User user = (User) get(userKey);
        //保险起见在存一遍cookie 和 redis
        if (user != null) {
            set(userKey, user, surviveTime, TimeUnit.HOURS);
            CookieUtil.setCookie(request, response, cookieKey, ticket);
        }
        return user;
    }

    @Override
    public void deleteUser(String ticket) {
        if (StringUtils.isEmpty(ticket)) {
            throw new GlobalException(RespBeanEnum.AUTH_ERROR);
        }
        //获取redis中的user key
        String userKey = RedisKeyUtil.getUserKey(ticket);
        remove(userKey);
    }

    /**
     * 描述: 当key不存在时做普通set操作，当key存在时，什么也不做。
     * 注意: redis版本需要升级到2.1以上，这时setIfAbsent方法才是原子操作
     * 本项目使用的是 redis 2.3
     *
     * @param key    redis key
     * @param value  redis value
     * @param expire 设置的超时时间
     * @return true：setnx成功，false:失败或者执行结果为null
     */
    @Override
    public boolean setNx(String key, Object value, long expire, TimeUnit timeUnit) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        Boolean result = ops.setIfAbsent(key, value, expire, timeUnit);
        return Boolean.TRUE.equals(result);
    }

    /**
     * 描述: 当key存在时做普通set操作，当key不存在时，什么也不做。
     * 注意: redis版本需要升级到2.1以上，这时setIfPresent方法才是原子操作
     *
     * @param key    redis key
     * @param value  redis value
     * @param expire 设置的超时时间
     * @return true：setex成功， false:失败或者执行结果为null
     */
    @Override
    public boolean setEx(String key, Object value, long expire, TimeUnit timeUnit) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        Boolean result = ops.setIfPresent(key, value, expire,timeUnit);
        return Boolean.TRUE.equals(result);
    }


    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        return set(key, value, -1L, TimeUnit.SECONDS);
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime, TimeUnit timeUnit) {
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value, expireTime, timeUnit);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(RespBeanEnum.SET_CACHE_ERROR);
        }
        return true;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(List<String> keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }


    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    @Override
    public Object get(final String key) {
        Object result = null;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            result = operations.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(RespBeanEnum.GET_CACHE_ERROR);
        }
        return result;
    }


}
