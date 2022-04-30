package com.zry.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 视图
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Api("用于主页跳转")
@Controller
public class IndexController {

    @GetMapping("/")
    public String home() {
        return "redirect:/index.html";
    }
}
