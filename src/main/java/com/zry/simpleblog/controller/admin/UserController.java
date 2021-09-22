package com.zry.simpleblog.controller.admin;

import com.zry.simpleblog.entity.User;
import com.zry.simpleblog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author zry
 * @ClassName UserController.java
 * @Description TODO
 * @createTime 2021年08月29日
 */
@Controller
@RequestMapping("/admin")
public class UserController {
    @Resource
    private UserService userService;
    @GetMapping("/login")
    public String loginPage(){
        return "admin/login";
    }
    @PostMapping("/login")
    public ModelAndView login(@RequestParam String username, @RequestParam String password, HttpSession session){
        System.out.println("this is login  Controller");
        ModelAndView mv = new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
//            将用户名和密码封装成UsernamePasswordToken对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                //执行登录
                subject.login(token);
            } catch (AuthenticationException e) {
                mv.addObject("message",e.getMessage());
                if (e instanceof IncorrectCredentialsException) {
                    mv.addObject("message","密码输入错误");
                }else {
                    mv.addObject("massage","预料之外的错误");
                }
                mv.addObject("username",username);
                System.out.println("登陆失败： " + e.getMessage());
                mv.setViewName("admin/login");
                return mv;
            }
        }
        User user = userService.checkUser(username);
        user.setPassword(null);
        session.setAttribute("User",user);
        mv.setViewName("redirect:/admin/");
        return mv;
    }

    @RequestMapping("/logout")
    public String logOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:admin/login";
    }
}
