package com.zry.simpleblog.service;

import com.github.pagehelper.PageInfo;
import com.zry.simpleblog.entity.Blog;
import com.zry.simpleblog.vo.BlogQuery;

import java.util.List;
import java.util.Map;

/**
 * @author zry
 * @ClassName BlogService.java
 * @Description TODO
 * @createTime 2021年09月01日
 */
public interface BlogService {

    PageInfo listBlog(int pageNum, int i);
    PageInfo listBlog(int pageNum, int i, BlogQuery blog);
    PageInfo listBlog(int pageNum, int i,String query);
    PageInfo listBlogByTagId(int pageNum, int i, Long tagId);

    Blog getAndConvert(Long id);
    void saveBlog(Blog blog) ;

    Blog getBlog(Long id);

    void deleteBlog(Long id);

    List<Blog> listRecommendBlogTop(int count);
    Map<String,List<Blog>> archiveBlog();

    Long countBlog();

    Blog getBlogByTitle(String title);
}
