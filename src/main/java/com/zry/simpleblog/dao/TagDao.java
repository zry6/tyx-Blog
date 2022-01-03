package com.zry.simpleblog.dao;

import com.zry.simpleblog.entity.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zry
 * @ClassName TagDao.java
 * @Description TODO
 * @createTime 2021年08月30日
 */
@Mapper
public interface TagDao {
    @Insert("insert into t_tag (name) values(#{name})")
    Boolean save(Tag tag);

    @Select("select * from t_tag where name=#{name}")
    Tag queryTagByName(String name);

    @Select("select * from t_tag")
    List<Tag> findAll();

    @Select("select * from t_tag where id=#{id}")
    Tag queryTagById(Long id);

    @Update("update t_tag set name=#{name} where id = #{id}")
    Boolean updateTag(Tag tag);

    @Delete("delete from t_tag where id=#{id};alter Table `t_tag` AUTO_INCREMENT=1;")
    void deleteTagById(Long id);
    @Delete("delete from t_blog_tags where tags_id=#{id}")
    void deleteTagBlogByTagId(Long id);

    @Select("select tags_id from t_blog_tags where blogs_id = #{blog_id}")
    List<Long> getTagIdByBlogId(Long blog_id);

    @Select("select id,name, count(*) as blogCount from" +
            "(select t_blog_tags.blogs_id,t_tag.* from t_tag ,t_blog_tags,t_blog where t_tag.id=t_blog_tags.tags_id and t_blog_tags.blogs_id=t_blog.id and t_blog.published=true ) tb " +
            "GROUP BY id  ORDER BY blogCount DESC  LIMIT #{count};")
    List<Tag> listTagTop(int count);

//    @Select("select * from t_blog where id in (select blogs_id from t_blog_tags where tags_id = #{tagId})")
//    List<Blog> queryBlogByTagId(Long tagId);

}

