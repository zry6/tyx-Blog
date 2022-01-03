package com.zry.simpleblog.controller;

import com.zry.simpleblog.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @author zry
 * @ClassName IndexController.java
 * @Description TODO
 * @createTime 2021年08月28日
 */
@Controller
public class IndexController {
    @Resource
    private BlogService blogService;
    @GetMapping("/")
    public String index(@RequestParam(value = "pageNum", defaultValue = "1", required = false) int pageNum, Model model) {
        model.addAttribute("pageInfo", blogService.listBlog(pageNum, 4));
        return "index";
    }
}
