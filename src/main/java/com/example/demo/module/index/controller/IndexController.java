package com.example.demo.module.index.controller;

import com.example.demo.module.novel.entity.Category;
import com.example.demo.module.novel.service.CategoryService;
import com.example.demo.module.user.entity.User;
import com.example.demo.util.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 跳转页面
 */
@Controller
@RequestMapping("/")
public class IndexController {

    public static final int INT = 999;
    @Autowired
    private CategoryService categoryService;
    /**
     * 首页
     *
     * @returndex
     */
    @RequestMapping("/")
    public String index(@SessionAttribute(required = false) User user, HttpServletRequest request,
                        ModelMap model) {
        model.addAttribute("user", user);
        //showType 显示类型 0：分类，1：列表
        String showType = request.getParameter("showType");
        if (StringUtils.isEmpty(showType)) {
            showType = "0";
            Map<String,Object> map = new HashMap<>();
            List<Category> list = categoryService.list(map);
            model.addAttribute("categorys",list);
        }
        model.addAttribute("showType", showType);
        return "index";
    }

    /**
     * 登录界面
     *
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 注册
     *
     * @return
     */
    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 小说添加页面
     *
     * @return
     */
    @RequestMapping("/novelAdd")
    public String novelAdd(@SessionAttribute(required = false) User user, ModelMap model) {
        if (user.getIdentify() != INT) {
            model.addAttribute("error", "没有使用权限");
            return "/errors/error";
        }
        return "novelAdd";
    }

    /**
     * 管理员页面
     *
     * @return
     */
    @RequestMapping("/admin")
    public String admin(@SessionAttribute(required = false) User user, ModelMap model) {
        if (user.getIdentify() != INT) {
            model.addAttribute("error", "没有使用权限");
            return "/errors/error";
        }
        return "admin";
    }

    /**
     * 小说类型添加页面
     *
     * @return
     */
    @RequestMapping("/categoryAdd")
    public String categoryAdd(@SessionAttribute(required = false) User user, ModelMap model) {
        if (user.getIdentify() != INT) {
            model.addAttribute("error", "没有使用权限");
            return "/errors/error";
        }
        return "categoryAdd";
    }
}
