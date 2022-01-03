package com.zry.simpleblog.controller;

import com.zry.simpleblog.entity.Blog;
import com.zry.simpleblog.entity.Tag;
import com.zry.simpleblog.service.BlogService;
import com.zry.simpleblog.service.TagService;
import com.zry.simpleblog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zry
 * @ClassName BlogShowController.java
 * @Description 博客前端控制器
 * @createTime 2021年10月11日
 */
@Controller
public class BlogShowController {
    @Resource
    private BlogService blogService;
    @Resource
    private TypeService typeService;
    @Resource
    private TagService tagService;

    @RequestMapping("/search")
    public String search(@RequestParam(value = "pageNum", defaultValue = "1", required = false) int pageNum, @RequestParam(value = "query") String query, Model model) {
        model.addAttribute("pageInfo", blogService.listBlog(pageNum, 6, query));
        model.addAttribute("query",query);
        return "search";
    }
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        Blog blog = blogService.getAndConvert(id);
        List<Tag> tags = tagService.getTagsByBlogId(id);
        if (tags!=null&&!tags.isEmpty()){
            blog.setTags(tags);
            blog.init();
        }
        model.addAttribute("blog",blog);
        return "blog";
    }

    @GetMapping("/footer/newBlog")
    public String newBlogs(Model model){
        model.addAttribute("newBlogs",blogService.listRecommendBlogTop(3));
        return "admin/_fragments :: newBlogList";
    }
}
