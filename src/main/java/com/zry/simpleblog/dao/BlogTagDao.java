package com.zry.simpleblog.dao;

import com.zry.simpleblog.entity.Blog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zry
 * @ClassName BlogTagDao.java
 * @Description TODO
 * @createTime 2021年09月04日
 */
@Mapper
public interface BlogTagDao {

    @Select("select * from t_blog where id in (select blogs_id from t_blog_tags where tags_id=#{tagId}) and published=true")
    List<Blog> queryBlogByTagId(Long tagId);

    @Insert("<script>INSERT INTO t_blog_tags" +
            " (blogs_id, tags_id) VALUES"
            + " <foreach collection = 'tagIds' item = 'tagId' index = 'index' separator = ','> "
            + " (#{blogId}, #{tagId}) "
            + " </foreach></script>")
    Boolean saveBlogAndTag(Long blogId, List tagIds);

    @Delete("delete from t_blog_tags where blogs_id = #{BlogId}")
    int deleteBlogTag(Long BlogId);

}
