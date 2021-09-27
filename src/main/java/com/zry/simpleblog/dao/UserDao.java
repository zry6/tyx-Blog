package com.zry.simpleblog.dao;

import com.zry.simpleblog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Select("select * from t_user where id=#{id}")
    User queryUserById(Long id);

    @Update("update t_user set username=#{username} ,password=#{password},nickname=#{nickname},email=#{email},update_time=#{updateTime} where id = #{id}")
    Boolean updateUser(User user);
}
