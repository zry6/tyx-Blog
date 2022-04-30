package com.zry.service;

import com.zry.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author zry
 * @create 2022-04-10 9:52
 */
public interface RedisService {
    /**
     * 功能描述: 存储user信息 在redis
     *
     * @param ticket,user;
     * @author zry
     */
    void saveUser(String ticket, User user,long surviveTime);

    /**
     * 功能描述: 根据ticket获取用户信息
     *
     * @param request,response,tick;
     * @return User;
     * @author zry
     */
    User getUserByTicket(HttpServletRequest request, HttpServletResponse response, String ticket);


    /**
     * 功能描述: 根据ticket删除用户信息
     * @param userKey;
     * @return User;
     * @author zry
     */
    void deleteUser(String userKey);

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
    boolean setNx(String key, Object value, long expire, TimeUnit timeUnit);

    /**
     * 描述: 当key存在时做普通set操作，当key不存在时，什么也不做。
     * 注意: redis版本需要升级到2.1以上，这时setIfPresent方法才是原子操作
     *
     * @param key    redis key
     * @param value  redis value
     * @param expire 设置的超时时间
     * @return true：setex成功， false:失败或者执行结果为null
     */
    boolean setEx(String key, Object value, long expire, TimeUnit timeUnit);

    Object get(final String key);

}
