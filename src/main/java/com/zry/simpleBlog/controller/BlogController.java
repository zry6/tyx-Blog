package com.zry.simpleBlog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zry.simpleBlog.comment.annotations.CheckLogin;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.dto.BlogDto;
import com.zry.simpleBlog.dto.BlogQuery;
import com.zry.simpleBlog.service.IBlogService;
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
     * 功能描述:根据博客id返回文章
     *
     * @param id 博客id
     * @CheckLogin(isLogin = false) 是指:
     * 将登录的用户信息加入UserContext中.
     * 若未登陆,UserContext中则为null.
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
     *
     * @return mv
     */
    @ApiOperation(value = "文章展示分页", notes = "文章展示分页: 可选参数[分类][标签],并且如果标签参数存在那么按照标签查询")
    @GetMapping("blogs")
    public RespBean blogPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "2") Integer pageSize, @RequestParam(required = false) Long typeId, @RequestParam(required = false) Long tagId) {
        //只展示已发布的文章
        Page<BlogDto> pageDto = null;
        if (tagId == null) {
            BlogQuery query = new BlogQuery();
            query.setTypeId(typeId);
            pageDto = blogService.blogPage(page, pageSize, query);
        } else {
            pageDto = blogService.blogPage(page, pageSize, tagId);
        }
        return RespBean.success(pageDto);
    }

}
