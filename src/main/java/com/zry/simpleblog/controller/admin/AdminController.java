package com.zry.simpleblog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author zry
 * @ClassName AdminController.java
 * @Description TODO
 * @createTime 2021年08月29日
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/")
    public String index(HttpSession session){
        return "admin/index";
    }
}
