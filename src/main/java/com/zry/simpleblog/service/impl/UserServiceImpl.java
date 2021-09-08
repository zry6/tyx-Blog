package com.zry.simpleblog.service.impl;

import com.zry.simpleblog.dao.UserDao;
import com.zry.simpleblog.entity.User;
import com.zry.simpleblog.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zry
 * @ClassName UserServiceImpl.java
 * @Description TODO
 * @createTime 2021年08月29日
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User checkUser(String username) {
        return userDao.queryUserByUsername(username);
    }
}
