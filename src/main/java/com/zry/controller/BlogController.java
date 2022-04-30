package com.zry.controller;


import com.zry.comment.annotations.CheckLogin;
import com.zry.comment.respBean.RespBean;
import com.zry.dto.BlogDto;
import com.zry.service.IBlogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


}
