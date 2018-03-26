package com.example.demo.module.index.controller;

import com.example.demo.module.user.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * 跳转页面
 */
@Controller
@RequestMapping("/")
public class IndexController {

    /**
     * 首页
     * @return
     */
    @RequestMapping("/")
    public String index(@SessionAttribute User user,@SessionAttribute Integer showType, ModelMap model){
        model.addAttribute("user",user);
        //showType 显示类型 0：分类，1：列表
        model.addAttribute("showType",showType);
        return "index";
    }

    /**
     * 登录界面
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * 注册
     * @return
     */
    @RequestMapping("/register")
    public String register(){
        return "register";
    }
}
