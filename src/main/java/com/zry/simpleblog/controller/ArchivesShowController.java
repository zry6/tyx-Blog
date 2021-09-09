package com.zry.simpleblog.controller;

import com.zry.simpleblog.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

/**
 * @author zry
 * @ClassName ArchivesShowController.java
 * @Description 归档页面
 * @createTime 2021年09月09日
 */
@Controller
public class ArchivesShowController {
    @Resource
    private BlogService blogService;
    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archiveMap",blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.countBlog());
        return "archives";
    }
}
