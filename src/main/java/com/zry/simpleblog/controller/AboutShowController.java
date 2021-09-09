package com.zry.simpleblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zry
 * @ClassName AboutShowController.java
 * @Description 关于我的页面
 * @createTime 2021年09月09日
 */
@Controller
public class AboutShowController {
    @GetMapping("/about")
    public String about(Model model){
            return "about";
    }
}
