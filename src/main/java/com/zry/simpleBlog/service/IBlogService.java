package com.zry.simpleBlog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.dto.BlogQuery;
import com.zry.simpleBlog.dto.PostBlogDto;
import com.zry.simpleBlog.entity.Blog;

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
     * @return
     */
    RespBean saveBlog(PostBlogDto blog);

    /**
     * 功能描述: 更新文章
     *
     * @param blogVo ;
     * @author zry
     * @create 2022/4/20
     * @return
     */
    RespBean updateBlog(PostBlogDto blogVo);

    /**
     * 功能描述: 博客分页 ，不用查询文章内容还可能有查询条件，所以为重写一下sql
     *
     * @param
     * @return
     * @author zry
     * @create 2022/4/25
     */
    RespBean blogPage(Integer current, Integer size, BlogQuery query);
    /**
     * 功能描述: 博客分页 ，不用查询文章内容还可能有查询条件，所以为重写一下sql
     *
     * @param
     * @return
     * @author zry
     * @create 2022/4/25
     */
    RespBean blogPage(Integer current, Integer size,  Long tagId);

    /**
     * 功能描述: 获得填充好的文章内容
     *
     * @param id ;
     * @return BlogDto;
     * @author zry
     * @create 2022/4/28
     */
    RespBean getBlogById(Integer id);
    /**
     * 功能描述: 删除博客
     *
     * @param
     * @return
     * @author zry
     * @create 2022/4/30
     */
    void deleteById(Long id);


    RespBean adminBlogPage(Integer current, Integer size, BlogQuery query);

    RespBean mapArchives();
}
