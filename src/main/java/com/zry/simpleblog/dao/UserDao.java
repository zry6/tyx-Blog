package com.zry.simpleblog.dao;

import com.zry.simpleblog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author zry
 * @ClassName UserDao.java
 * @Description TODO
 * @createTime 2021年08月29日
 */
@Mapper
public interface UserDao {

    @Select("select * from t_user where username=#{username}")
    User queryUserByUsername(String username);

    @Select("select id,avatar,create_time,email,nickname,password,type,update_time,username from t_user where id=#{id}")
    User queryUserById(Long id);
}
