package com.zry.simpleBlog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zry.simpleBlog.comment.aop.annotations.*;
import com.zry.simpleBlog.comment.enums.AuthEnum;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.comment.enums.RespBeanEnum;
import com.zry.simpleBlog.comment.enums.IdempotentStrategyEnum;
import com.zry.simpleBlog.dto.BlogDto;
import com.zry.simpleBlog.dto.BlogQuery;
import com.zry.simpleBlog.dto.PostBlogDto;
import com.zry.simpleBlog.service.IBlogService;
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
     * 后台博客分页
     *
     * @return mv
     */
    @AuthCheck(rank = AuthEnum.USER)
    @ApiOperation(value = "后台文章分页", notes = "可按条件查询")
    @GetMapping("/blogs")
    public RespBean blogs(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer pageSize, BlogQuery query) {
        Page<BlogDto> dtoPage = blogService.adminBlogPage(page, pageSize, query);
        return RespBean.success(dtoPage);
    }

    /**
     * 新增文章
     */
    @Idempotent(timeout = 100, strategy = IdempotentStrategyEnum.IDEMPOTENT_INTERFACE)
    @ApiOperation(value = "添加文章", notes = "实现幂等性")
    @PostMapping("/blogs")
    @AuthCheck(rank = AuthEnum.ADMINISTRATOR)
    @LogWeb(title = "博客管理", action = "新增文章")
    public RespBean post(@RequestBody @Valid PostBlogDto blogDto) {
        return RespBean.success(RespBeanEnum.POST_SUCCESS, blogService.saveBlog(blogDto));
    }

    /**
     * 更新文章
     */
    @ApiOperation(value = "更新文章")
    @PutMapping("/blogs/{id}")
    @AuthCheck(rank = AuthEnum.ADMINISTRATOR)
    @LogWeb(title = "博客管理", action = "更新文章")
    public RespBean update(@RequestBody @Valid PostBlogDto blogDto, @PathVariable Long id) {
        blogDto.setId(id);
        blogService.updateBlog(blogDto);
        return RespBean.success(RespBeanEnum.UPDATE_SUCCESS);
    }

    /**
     * 删除博客
     *
     */
    @ApiOperation(value = "删除文章")
    @AuthCheck(rank = AuthEnum.ADMINISTRATOR)
    @DeleteMapping("/blogs/{id}")
    public RespBean delete(@PathVariable Long id) {
        blogService.deleteById(id);
        return RespBean.success(RespBeanEnum.DELETE_SUCCESS);
    }
}
