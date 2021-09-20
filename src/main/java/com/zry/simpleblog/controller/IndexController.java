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
 * @ClassName IndexController.java
 * @Description TODO
 * @createTime 2021年08月28日
 */
@Controller
public class IndexController {
    @Resource
    private BlogService blogService;
    @Resource
    private TypeService typeService;
    @Resource
    private TagService tagService;

    @GetMapping("/")
    public String index(@RequestParam(value = "pageNum", defaultValue = "1", required = false) int pageNum, Model model) {
        model.addAttribute("pageInfo", blogService.listBlog(pageNum, 4));
//        model.addAttribute("types", typeService.listTypeTop(6));
//        model.addAttribute("tags", tagService.listTagTop(8));
//        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(7));
        return "index";
    }

    @RequestMapping("/search")
    public String search(@RequestParam(value = "pageNum", defaultValue = "1", required = false) int pageNum, @RequestParam(value = "query") String query, Model model) {
        model.addAttribute("pageInfo", blogService.listBlog(pageNum, 6, query));
        model.addAttribute("query",query);
        return "search";
    }
    @GetMapping("/blog/{id}")
    public String blog( @PathVariable Long id,Model model){
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
        return "_fragments :: newBlogList";
    }
}
