package com.zry.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zry.dto.BlogDto;
import com.zry.dto.BlogQuery;
import com.zry.dto.PostBlogDto;
import com.zry.entity.Blog;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
public interface IBlogService extends IService<Blog> {
    /**
     * 功能描述: 新增文章
     *
     * @param blog ;
     * @author zry
     * @create 2022/4/20
     */
    PostBlogDto saveBlog(PostBlogDto blog);

    /**
     * 功能描述: 更新文章
     *
     * @param blogVo ;
     * @author zry
     * @create 2022/4/20
     */
    void updateBlog(PostBlogDto blogVo);

    /**
     * 功能描述: 后台管理博客分页 ， 不用查询文章内容，所以为重写一下sql
     *
     * @param
     * @return
     * @author zry
     * @create 2022/4/25
     */
    Page<BlogDto> allBlogPageNoContent(Integer current, Integer size, BlogQuery query);


    /**
     * 功能描述: 获得填充好的文章内容
     *
     * @param id ;
     * @return BlogDto;
     * @author zry
     * @create 2022/4/28
     */
    BlogDto getBlogById(Integer id);
    /**
     * 功能描述: 删除博客
     *
     * @param
     * @return
     * @author zry
     * @create 2022/4/30
     */
    void deleteById(Long id);

    /**
     * 功能描述: 查询已发布的文章 分页 ， 不用查询文章内容，，所以为重写一下sql
     *
     * @param
     * @return
     * @author zry
     * @create 2022/4/25
     */
}
