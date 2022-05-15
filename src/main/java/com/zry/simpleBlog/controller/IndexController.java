package com.zry.simpleBlog.controller;

import com.zry.simpleBlog.service.IBlogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

/**
 * 视图
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */

@Controller
public class IndexController {

    @Resource
    private IBlogService blogService;

    @ApiOperation(value = "用于主页跳转")
    @GetMapping("/")
    public String home() {
        return "redirect:/p/index.html";
    }


}
