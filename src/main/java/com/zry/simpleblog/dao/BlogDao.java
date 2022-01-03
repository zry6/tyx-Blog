package com.zry.simpleblog.dao;

import com.zry.simpleblog.util.SqlProvider;
import com.zry.simpleblog.entity.Blog;
import com.zry.simpleblog.vo.BlogQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zry
 * @ClassName BlogDao.java
 * @Description TODO
 * @createTime 2021年09月01日
 */
@Mapper
public interface BlogDao {

    @Results({@Result(property = "type.id", column = "type_id"),
            @Result(property = "user.id", column = "user_id")})
    @SelectProvider(type = SqlProvider.class, method = "selectBlog")
    List<Blog> findAll(BlogQuery blog);

    @Results({@Result(property = "type.id", column = "type_id"),
            @Result(property = "user.id", column = "user_id")})
    @Select("select * from t_blog where published=true ORDER BY update_time DESC")
    List<Blog> findAllPublished();

    @Results({@Result(property = "type.id", column = "type_id"),
            @Result(property = "user.id", column = "user_id")})
    @Select(" select * from t_blog" +
            "        where (title like concat('%',#{query},'%')" +
            "        or description like concat('%',#{query},'%')" +
            "        or content like concat('%',#{query},'%'))and published=true")
    List<Blog> findByQuery(String query);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into t_blog (appreciation,commentable,content,create_time," +
            "description,first_picture,flag,published,recommend,share_statement,title," +
            "update_time,views,type_id,user_id)" +
            "values(#{appreciation},#{commentable},#{content},#{createTime}," +
            "#{description},#{firstPicture},#{flag},#{published},#{recommend},#{shareStatement}," +
            "#{title},#{updateTime},#{views},#{type.id},#{user.id});alter Table `t_tag` AUTO_INCREMENT=1;")
    Boolean save(Blog blog);


    @Results({@Result(property = "type.id", column = "type_id"),
            @Result(property = "type.name", column = "type_name"),
            @Result(property = "user.id", column = "user_id")})
    @Select("select t_blog.* ,t_type.name as type_name from t_blog , t_type where t_blog.id = #{id} and t_blog.type_id=t_type.id")
    Blog queryBlog(Long id);


    @Results({@Result(property = "type.id", column = "type_id"),
            @Result(property = "type.name", column = "type_name"),
            @Result(property = "user.id", column = "user_id")})
    @Select("select t_blog.* ,t_type.name as type_name from t_blog , t_type where t_blog.id = #{id} and t_blog.published=true and t_blog.type_id=t_type.id")
    Blog queryBlogPublished(Long id);


    @Update("update t_blog SET appreciation=#{appreciation},commentable=#{commentable},content=#{content},description=#{description}," +
            "first_picture=#{firstPicture},flag=#{flag},published=#{published},recommend=#{recommend},share_statement=#{shareStatement}," +
            "title=#{title},update_time=#{updateTime},type_id=#{type.id} where id=#{id}")
    Boolean updateBlog(Blog blog);

    @Delete("delete from t_blog where id=#{id}")
    int deleteBlog(Long id);

    @Select("select * from t_blog where recommend=true ORDER BY update_time DESC  LIMIT #{count};")
    List<Blog> listRecommendBlogTop(int count);

    @Select("select * from t_blog where type_id = #{typeId}")
    List<Blog> queryBlogByTypeId(Long typeId);

    @Update("update t_blog set views=views+1 where id = #{id}")
    void updateViews(Long id);
    @Select("select date_format(b.update_time,'%Y') as year from t_blog b where published=true group by year order by year desc")
    List<String> findGroupYear();
    @Select("select * from t_blog b where date_format(b.update_time,'%Y') = #{year} and published=true  order by update_time desc;;")
    List<Blog> findBlogByYear(String year);
    @Select("select count(*) from t_blog where  published=true")
    Long count();
    @Select("select * from t_blog where  title=#{title}")
    Blog getBlogByTitle(String title);
}