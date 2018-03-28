package com.example.demo.module.user.controller;

import com.example.demo.module.user.entity.User;
import com.example.demo.module.user.service.UserService;
import com.example.demo.util.ResultJson;
import com.example.demo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户相关
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam(value = "img", required = false) MultipartFile imgFile,
                           HttpServletRequest request, User user, BindingResult userloginResult, ModelMap mav) {
        String img = Utils.saveImg(imgFile, request,"/img/user");
        if (null == img) {
            mav.addAttribute("error", "图片上传失败");
            return "/errors/error";
        }
        ResultJson find = userService.findByUsername(user.getUsername());
        if (find.getSuccess()) {
            mav.addAttribute("error", "该用户已存在");
            return "/errors/error";
        }
        user.setIdentify(0);
        user.setImg(img);
        user.setStatus(0);
        ResultJson save = userService.save(user);
        if (save.getSuccess()) {
            return "login";
        } else {
            mav.addAttribute("error", "注册失败");
            return "/errors/error";
        }
    }


    @RequestMapping(value = "/findUser", method = RequestMethod.POST)
    @ResponseBody
    public ResultJson findUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        return userService.findByUsername(username);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultJson login(HttpServletRequest request, String username, String password) {
        ResultJson json = userService.findByUsername(username);
        if (json.getSuccess()) {
            User user = (User) json.getObj();
            password = Utils.md5Hex(password, String.valueOf(user.getSalt()));
            if (password.equals(user.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(1800);
                return json.setSuccess(true);
            } else {
                return json.setSuccess(false).setMsg("密码错误");
            }
        }
        return json;
    }
}
