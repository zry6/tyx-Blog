package com.zry.simpleblog.dao;

import com.zry.simpleblog.entity.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zry
 * @ClassName TypeDao.java
 * @Description TODO
 * @createTime 2021年08月30日
 */
@Mapper
public interface TypeDao {
    @Insert("insert into t_type (name) values(#{name})")
    Boolean save(Type type);

    @Select("select * from t_type where name=#{name}")
    Type queryTypeByName(String name);

    @Select("select * from t_type")
    List<Type> findAll();
    @Select("select * from t_type where id=#{id} ")
    Type queryTypeById(Long id);

    @Update("update t_type set name=#{name} where id = #{id}")
    Boolean updateType(Type type);

    @Delete("delete from t_type where id=#{id};alter Table `t_type` AUTO_INCREMENT=1")
    void deleteTypeById(Long id);

    @Select("select id,name, count(*) as blogCount FROM " +
            "(select t_type.*,t_blog.id as blog_id from (t_blog,t_type)where t_blog.type_id=t_type.id and published=true) t  " +
            "GROUP BY id  ORDER BY blogCount DESC  LIMIT #{i}")
    List<Type> listTypeTop(int i);
}
