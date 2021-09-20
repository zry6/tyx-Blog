package com.zry.simpleblog.controller.admin;

import com.zry.simpleblog.entity.Blog;
import com.zry.simpleblog.entity.Tag;
import com.zry.simpleblog.entity.User;
import com.zry.simpleblog.service.BlogService;
import com.zry.simpleblog.service.TagService;
import com.zry.simpleblog.service.TypeService;
import com.zry.simpleblog.vo.BlogQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zry
 * @description
 * @title
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    /**
     * 各种跳转方法构建
     */
    private static final String INPUT = "admin/blog-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";


    @Resource
    private BlogService blogService;
    @Resource
    private TypeService typeService;
    @Resource
    private TagService tagService;

    /**
     * 博客列表浏览
     *
     * @return mv
     */
    @GetMapping("/blogs")
    public ModelAndView blogs(@RequestParam(value = "pageNum", defaultValue = "1", required = false) int pageNum,
                              BlogQuery blog) {
        ModelAndView mv = new ModelAndView();
        //初始化分类，查询所有分类
        mv.addObject("types", typeService.listType());
        mv.addObject("pageInfo", blogService.listBlog(pageNum, 5, blog));
        mv.setViewName(LIST);
        return mv;
    }

    /**
     * 博客列表浏览 中局部搜索
     *
     * @description
     * @title
     * @author zry
     */
    @PostMapping("/blogs/search")
    public ModelAndView search(@RequestParam(value = "pageNum", defaultValue = "1", required = false) int pageNum,
                               BlogQuery blog) {
        ModelAndView mv = new ModelAndView();
        //初始化分类，查询所有分类
        mv.addObject("pageInfo", blogService.listBlog(pageNum, 6, blog));
        mv.setViewName("admin/blogs :: blogList");
        return mv;
    }

    /**
     * 博客添加页面
     *
     * @param
     * @return mv
     */
    @GetMapping("/blogs/input")
    public ModelAndView input() {
        ModelAndView mv = new ModelAndView();
        setTypeAndTag(mv);
        mv.addObject("blog", new Blog());
        mv.setViewName(INPUT);
        return mv;
    }

    /**
     * 编辑博客
     *
     * @description
     * @title
     * @author zry
     */
    @GetMapping("/blogs/{id}/input")
    public ModelAndView editInput(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView();
        setTypeAndTag(mv);
        Blog blog = blogService.getBlog(id);
        List<Tag> tags = tagService.getTagsByBlogId(id);
        if (tags != null && !tags.isEmpty()) {
            blog.setTags(tags);
            blog.init();
        }
        mv.addObject("blog", blog);
        mv.setViewName(INPUT);
        return mv;
    }

    /**
     * 公用（保存和修改）
     *
     * @param blog attributes session
     * @return REDIRECT_LIST
     */

    @PostMapping("/blogs")
    public String post(Blog blog,RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("User"));
        try {
            blog.setType(typeService.findTypeById(blog.getType().getId()));
            blog.setTags(tagService.listTag(blog.getTagIds()));
            attributes.addFlashAttribute("message", "操作成功");
            blogService.saveBlog(blog);
        } catch (Exception e) {
            System.out.println("=====e:"+e);
            attributes.addFlashAttribute("message", "操作失败");
        }
        return REDIRECT_LIST;
    }


    /**
     * 添加时的初始化，查询签分类和标
     *
     * @param mv
     */
    private void setTypeAndTag(ModelAndView mv) {
        mv.addObject("types", typeService.listType());
        mv.addObject("tags", tagService.listTag());
    }


    /**
     * 删除博客
     *
     * @param id attributes
     * @return
     */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        attributes.addFlashAttribute("message", "删除成功");
        try {
            blogService.deleteBlog(id);
        } catch (Exception e) {
            System.out.println("deleteBlog 异常::" + e);
            attributes.addFlashAttribute("message", "删除失败");
        }
        return REDIRECT_LIST;
    }

}
