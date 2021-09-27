package com.zry.simpleblog.service.impl;

import com.zry.simpleblog.dao.UserDao;
import com.zry.simpleblog.entity.User;
import com.zry.simpleblog.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

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

    @Transactional
    @Override
    public User updateUser(User user) {
        user.setUpdateTime(new Date());
        if ( userDao.updateUser(user)){
            return userDao.queryUserById(user.getId());
        }else{
            return null;
        }

    }
}
