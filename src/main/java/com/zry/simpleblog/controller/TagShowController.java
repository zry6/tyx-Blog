package com.zry.simpleblog.controller;

import com.zry.simpleblog.entity.Tag;
import com.zry.simpleblog.service.BlogService;
import com.zry.simpleblog.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zry
 * @ClassName TypeShowController.java
 * @Description TODO
 * @createTime 2021年09月07日
 */
@Controller
public class TagShowController {
    @Resource
    private TagService tagService;
    @Resource
    private BlogService blogService;
    @GetMapping("/tags/{id}")
    public String types(@PathVariable Long id, @RequestParam(value = "pageNum", defaultValue = "1", required = false) int pageNum , Model model) {
        List<Tag> tags = tagService.listTagTop(10000);
        if (id == -1) {
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("pageInfo", blogService.listBlogByTagId(pageNum,6,id));
        model.addAttribute("activeTagId", id);
        return "types";
    }
}
