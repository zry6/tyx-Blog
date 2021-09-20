package com.zry.simpleblog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.zry.simpleblog.entity.Type;
import com.zry.simpleblog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * @author zry
 * @ClassName TypeController.java
 * @Description TODO
 * @createTime 2021年08月30日
 */
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Resource
    private TypeService typeService;

    /**
     * 分页形式的分页管理
     *
     * @description
     * @title
     * @author zry
     */
    @GetMapping("/types")
    public ModelAndView typeList(@RequestParam(value = "pageNum", defaultValue = "1", required = false) int pageNum) {

        ModelAndView mv = new ModelAndView();
        PageInfo pageInfo = typeService.listType(pageNum, 6);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("admin/types");
        return mv;
    }

    /**
     * 修改的接口
     *
     * @param id
     * @param
     * @return
     */
    @GetMapping("/types/{id}")
    public ModelAndView editInput(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("type", typeService.findTypeById(id));
        mv.setViewName("admin/types-input");
        return mv;
    }

    /**
     * 添加的接口
     *
     * @param model
     * @return
     */
    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    /**
     * 修改的接口
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.findTypeById(id));
        return "admin/types-input";
    }

    /**
     * 添加数据的提交
     *
     * @param type
     * @param attributes
     * @return
     */
    @PostMapping("/types")
    public String post(Type type, RedirectAttributes attributes) {
        Type type1 = typeService.findTypeByName(type);
        if (type1 != null) {
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "admin/types-input";
        }
        if (typeService.saveType(type)) {
            attributes.addFlashAttribute("message", "新增成功");
        } else {
            attributes.addFlashAttribute("message", "新增失败");
        }
        return "redirect:/admin/types";
    }

    /**
     * 修改数据的提交
     *
     * @param type
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/types/{id}")
    public String editPost(Type type, @PathVariable Long id, RedirectAttributes attributes) {
        Type type1 = typeService.findTypeByName(type);
        if (type1 != null) {
            attributes.addFlashAttribute("message", "这是一个重复的分类");

            return "admin/types-input";
        }
        if (typeService.updateType(id, type)) {
            attributes.addFlashAttribute("message", "更新成功");
        } else {
            attributes.addFlashAttribute("message", "更新失败");
        }
        return "redirect:/admin/types";
    }

    /**
     * 删除数据
     *
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
