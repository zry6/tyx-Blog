package com.zry.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zry.comment.annotations.CheckLogin;
import com.zry.comment.respBean.RespBean;
import com.zry.dto.BlogDto;
import com.zry.dto.BlogQuery;
import com.zry.service.IBlogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
@RestController
public class BlogController {
    @Resource
    private IBlogService blogService;

    /**
     * 功能描述:根据博客id返回
     * @CheckLogin(isLogin = false) 是指:
     * 将登录的用户信息加入UserContext中.
     * 若未登陆,UserContext中则为null.
     * @param id 博客id
     * @author zry
     * @create 2022/4/28
     */
    @CheckLogin(isLogin = false)
    @GetMapping("blogs/{id}")
    public RespBean getBlog(@PathVariable Integer id) {
        BlogDto blog = blogService.getBlogById(id);
        return RespBean.success(blog);
    }

    /**
     * 首页文章展示分页
     * @return mv
     */
    @ApiOperation(value = "文章展示分页",notes = "首页文章展示分页")
    @GetMapping("blogs")
    public RespBean indexBlogs(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "8") Integer size) {
        //只展示已发布的文章
        BlogQuery query = new BlogQuery();
        query.setPublished(true);
        Page<BlogDto> page = blogService.blogPageNoContent(current, size,query);
        return RespBean.success(page);
    }
    /**
     * 分类页文章展示分页
     * @return mv
     */
    @ApiOperation(value = "分类页文章分页",notes = "分类页文章展示分页")
    @GetMapping("typeBlogs")
    public RespBean typeBlogs(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "8") Integer size,@RequestParam Long typeId) {
        //只展示已发布的文章
        BlogQuery query = new BlogQuery();
        query.setPublished(true);
        query.setTypeId(typeId);
        Page<BlogDto> page = blogService.blogPageNoContent(current, size,query);
        return RespBean.success(page);
    }

    /**
     * 标签页文章展示分页
     * @return mv
     */
    @ApiOperation(value = "标签页文章分页",notes = "标签页文章展示分页")
    @GetMapping("tagBlogs")
    public RespBean tagBlogs(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "8") Integer size,@RequestParam Long tags) {
//        //只展示已发布的文章
//        BlogQuery query = new BlogQuery();
//        query.setPublished(true);
//        query.setTypeId(typeId);
//        Page<BlogDto> page = blogService.blogPageNoContent(current, size,query);

        return RespBean.success();
    }
}
