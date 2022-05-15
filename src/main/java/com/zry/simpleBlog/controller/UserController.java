package com.zry.simpleBlog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.entity.User;
import com.zry.simpleBlog.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @GetMapping("/{id}")
    public RespBean getBlog(@PathVariable Integer id) {
        User user = userService.getOne(new QueryWrapper<User>().select("id","avatar","nickname","introduction","email").eq("id",id));
        return RespBean.success(user);
    }
}
