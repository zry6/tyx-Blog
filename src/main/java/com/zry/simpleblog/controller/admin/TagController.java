package com.zry.simpleblog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.zry.simpleblog.entity.Tag;
import com.zry.simpleblog.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * @author zry
 * @ClassName TagController.java
 * @Description TODO
 * @createTime 2021年08月30日
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Resource
    private TagService tagService;
/**分页形式的分页管理
 * @description
 * @title
 * @author zry
 */
    @GetMapping("/tags")
    public ModelAndView tagList(@RequestParam(value = "pageNum", defaultValue = "1", required = false) int pageNum) {

        ModelAndView mv = new ModelAndView();
        PageInfo pageInfo = tagService.listTag(pageNum, 6);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("admin/tags");
        return mv;
    }
/**进入添加标签页面
 * @description
 * @title
 * @author zry
 */
    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }


    /**
     * 修改的接口
     * @param id
     * @param
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public ModelAndView editInput(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("tag", tagService.findTagById(id));
        mv.setViewName("admin/tags-input");
        return mv;
    }

    /**增加标签的方法
     * @description
     * @title
     * @author zry
     */
    @PostMapping("/tags")
    public ModelAndView addTags(Tag tag, RedirectAttributes attributes) {
        ModelAndView mv = new ModelAndView();
        Tag tagByName = tagService.findTagByName(tag);
        if (tagByName == null) {
            if (tagService.addTag(tag)) {
                attributes.addFlashAttribute("message", "新增成功");
                mv.setViewName("redirect:/admin/tags");
            } else {
                mv.addObject("message", "添加失败");
                mv.setViewName("admin/tags-input");
            }
        } else {
            mv.addObject("message", "标签已存在");
            mv.setViewName("admin/tags-input");
        }
        return mv;
    }
    /**
     * 修改数据的提交
     * @param tag
     * @param result
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/tags/{id}")
    public ModelAndView editPost(Tag tag,  @PathVariable Long id, RedirectAttributes attributes) {
        ModelAndView mv = new ModelAndView();

        Tag tagById = tagService.findTagByName(tag);
        if (tagById == null) {
            if (tagService.updateTag(id,tag)) {
                attributes.addFlashAttribute("message", "更新成功");
            } else {
                attributes.addFlashAttribute("message", "更新失败");
            }
            mv.setViewName("redirect:/admin/tags");
        }else {
            mv.addObject("message","标签已存在");
            mv.setViewName("admin/tags-input");
        }

        return mv;
    }
    /**
     * 删除数据
     * @param id
     * @param attributes
     */
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }
}
