package com.zry.simpleblog.dao;

import com.zry.simpleblog.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zry
 * @ClassName CommentDao.java
 * @Description TODO
 * @createTime 2021年09月04日
 */
@Mapper
public interface CommentDao {
    @Delete("SET FOREIGN_KEY_CHECKS = 0;delete from t_comment where blog_id=#{blogId};SET FOREIGN_KEY_CHECKS = 1;")
    int deleteComment(Long blogId);

    @Results({@Result(property = "blog.id", column = "blog_id"),
            @Result(property = "parentComment.id", column = "parent_comment_id")})
    @Select("select * from t_comment where id=#{parentId}")
    Comment findParentById(Long parentId);

    @Results({@Result(property = "blog.id", column = "blog_id"),
            @Result(property = "parentComment.id", column = "parent_comment_id")})
    @Insert("SET FOREIGN_KEY_CHECKS = 0; insert into t_comment(admin_comment,avatar,content,create_time,email,nickname,blog_id,parent_comment_id)" +
            "values(#{adminComment},#{avatar},#{content},#{createTime},#{email},#{nickname},#{blog.id},#{parentComment.id}); SET FOREIGN_KEY_CHECKS = 1;")
    Boolean save(Comment comment);
    @Results({@Result(property = "blog.id", column = "blog_id"),
            @Result(property = "parentComment.id", column = "parent_comment_id")})
    @Select("select * from t_comment where blog_id=#{blogId} and parent_comment_id is NULL ORDER BY create_time ASC")
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId);

    @Results({@Result(property = "blog.id", column = "blog_id"),
            @Result(property = "parentComment.id", column = "parent_comment_id")})
    @Select("select * from t_comment where parent_comment_id=#{id}")
    List<Comment> getReplyComments(Long id);
    @Results({@Result(property = "blog.id", column = "blog_id"),
            @Result(property = "parentComment.id", column = "parent_comment_id")})
    @Select("select * from t_comment where blog_id=(select id from t_blog where title=#{title}) and parent_comment_id is NULL ORDER BY create_time ASC")
    List<Comment> findByBlogTitleAndParentCommentNull(String title);
}
