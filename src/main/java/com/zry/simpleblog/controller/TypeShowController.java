package com.zry.simpleblog.controller;

import com.zry.simpleblog.entity.Type;
import com.zry.simpleblog.service.BlogService;
import com.zry.simpleblog.service.TypeService;
import com.zry.simpleblog.vo.BlogQuery;
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
public class TypeShowController {
    @Resource
    private TypeService typeService;
    @Resource
    private BlogService blogService;
    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id, @RequestParam(value = "pageNum", defaultValue = "1", required = false) int pageNum , Model model) {
        List<Type> types = typeService.listTypeTop(10000);
        if (id == -1) {
            id = types.get(0).getId();
        }

        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types", types);
        model.addAttribute("pageInfo", blogService.listBlog(pageNum,6,blogQuery));
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
