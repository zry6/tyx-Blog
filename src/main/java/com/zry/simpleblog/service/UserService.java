package com.zry.simpleblog.service;

import com.zry.simpleblog.entity.User;

/**
 * @author zry
 * @ClassName UserService.java
 * @Description TODO
 * @createTime 2021年08月29日
 */
public interface UserService {

    User checkUser(String username);
}
