package com.zry.simpleblog.util;

import com.zry.simpleblog.vo.BlogQuery;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author zry
 * @ClassName SqlProvider.java
 * @Description TODO
 * @createTime 2021年09月01日
 */
public class SqlProvider {
    public String selectBlog(BlogQuery blog) {
        return new SQL() {{
            SELECT("*");
            FROM("t_blog");
            if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                WHERE("title like '%" + blog.getTitle() + "%'");
            }
            if (blog.getTypeId() != null && blog.getTypeId()!=0) {
                WHERE("type_id = " + blog.getTypeId());
            }
            if (blog.isRecommend()) {
                WHERE("recommend = " + blog.isRecommend());
            }
            if (blog.getUserId()!=null && blog.getUserId()!=0){
                WHERE("user_id = " + blog.getUserId());
            }
            ORDER_BY("update_time desc");
//            desc
        }}.toString();

    }
}
