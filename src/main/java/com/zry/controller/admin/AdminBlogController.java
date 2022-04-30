package com.zry.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zry.comment.annotations.CheckLogin;
import com.zry.comment.annotations.Idempotent;
import com.zry.comment.annotations.IdempotentStrategy;
import com.zry.comment.annotations.LogAnnotation;
import com.zry.comment.respBean.RespBean;
import com.zry.comment.respBean.RespBeanEnum;
import com.zry.dto.BlogDto;
import com.zry.dto.BlogQuery;
import com.zry.dto.PostBlogDto;
import com.zry.service.IBlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 后台管理文章的接口
 *
 * @author zry
 * @since 2022-04-09
 */

@Api(tags = "后台文章管理接口")
@RestController
@RequestMapping("/admin")
public class AdminBlogController {

    @Resource
    private IBlogService blogService;

    /**
     * 各种跳转方法构建
     */
    private static final String INPUT = "admin/blog-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    /**
     * 后台博客分页
     * @return mv
     */
    @CheckLogin
    @ApiOperation(value = "后台文章分页",notes = "可按条件查询")
    @GetMapping("/blogs")
    public RespBean blogs(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "5") Integer size, BlogQuery query) {
        Page<BlogDto> page = blogService.allBlogPageNoContent(current, size,query);
        return RespBean.success(page);
    }


    /**
     * 博客添加页面
     *
     * @param
     * @return mv
     */
    @GetMapping("/blogs/input")
    public void input() {

    }

    /**
     * 新增文章
     */
    @Idempotent(timeout = 100,strategy = IdempotentStrategy.IDEMPOTENT_INTERFACE)
    @ApiOperation(value = "添加文章")
    @PostMapping("/blogs")
    @CheckLogin
    @LogAnnotation(title = "博客管理", action = "新增文章")
    public RespBean post(@RequestBody @Valid PostBlogDto blogDto) {
        PostBlogDto vo = blogService.saveBlog(blogDto);
        return RespBean.success(RespBeanEnum.POST_SUCCESS, vo.getId());
    }

    /**
     * 更新文章
     */
    @ApiOperation(value = "更新文章")
    @PutMapping("/blogs/{id}")
    @CheckLogin
    @LogAnnotation(title = "博客管理", action = "更新文章")
    public RespBean update(@RequestBody @Valid PostBlogDto blogDto, @PathVariable Long id) {
        blogDto.setId(id);
        blogService.updateBlog(blogDto);
        return RespBean.success(RespBeanEnum.UPDATE_SUCCESS);
    }
    /**
     * 删除博客
     *
     * @param id ;
     * @return
     */
    @ApiOperation(value = "删除文章")
    @CheckLogin
    @DeleteMapping("/blogs/{id}")
    public RespBean delete(@PathVariable Long id) {
        blogService.deleteById(id);
        return RespBean.success(RespBeanEnum.DELETE_SUCCESS);
    }
}
