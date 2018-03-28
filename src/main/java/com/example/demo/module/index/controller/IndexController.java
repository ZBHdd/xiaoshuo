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

    public static final int INT = 999;

    /**
     * 首页
     * @returndex
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

    /**
     * 小说页面
     * @return
     */
    @RequestMapping("/novelAdd")
    public String novelAdd(@SessionAttribute User user, ModelMap model){
        if(user.getIdentify() != INT){
            model.addAttribute("error","没有使用权限");
            return "/errors/error";
        }
        return "novelAdd";
    }

    /**
     * 小说类型页面
     * @return
     */
    @RequestMapping("/categoryAdd")
    public String categoryAdd(@SessionAttribute User user, ModelMap model){
        if(user.getIdentify() != INT){
            model.addAttribute("error","没有使用权限");
            return "/errors/error";
        }
        return "categoryAdd";
    }
}
