package com.example.demo.module.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Utils;
import com.example.demo.module.user.entity.User;
import com.example.demo.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

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
    public String register(@RequestParam(value = "img",required = false)MultipartFile imgFile,
                           HttpServletRequest request, User user, BindingResult userloginResult,ModelMap mav) {
        String img = saveImg(imgFile, request);
        if (null == img){
            mav.addAttribute("error","图片上传失败");
            return "/errors/error";
        }
        JSONObject find = userService.findByUsername(user.getUsername());
        if((Boolean) find.get("success")){
            mav.addAttribute("error","该用户已存在");
            return "/errors/error";
        }
        user.setIdentify(0);
        user.setImg(img);
        user.setStatus(0);
        JSONObject save = userService.save(user);
        if((Boolean)save.get("success")){
            return "login";
        }else {
            mav.addAttribute("error","注册失败");
            return "/errors/error";
        }
    }


    @RequestMapping(value = "/findUser", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        return userService.findByUsername(username);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject login(HttpServletRequest request,String username,String password) {
        JSONObject json = userService.findByUsername(username);
        if((Boolean) json.get("success")){
           User  user = (User)json.get("obj");
            password = Utils.md5Hex(password ,String.valueOf(user.getSalt()));
            if(password.equals(user.getPassword())){
                HttpSession session = request.getSession();
                session.setAttribute("token",user.getUsername());
                session.setMaxInactiveInterval(1800);
                json.put("success",true);
                return json;
            }else {
                json.put("success",false);
                json.put("msg","密码错误");
                return json;
            }
        }
        return json;
    }

    private String saveImg(MultipartFile img, HttpServletRequest request) {
        /**页面控件的文件流**/
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
        /**构建图片保存的目录**/
        String logoPathDir = "/files" + dateformat.format(new Date());
        /**得到图片保存目录的真实路径**/
        String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);
        /**根据真实路径创建目录**/
        File logoSaveFile = new File(logoRealPathDir);
        if (!logoSaveFile.exists()) logoSaveFile.mkdirs();
        /**页面控件的文件流**/ /**获取文件的后缀**/
        String suffix = img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf("."));
        // /**使用UUID生成文件名称**/ //
        String logImageName = UUID.randomUUID().toString() + suffix;
        //构建文件名称
//        String logImageName = img.getOriginalFilename();
        /**拼成完整的文件保存路径加文件**/
        String fileName = logoRealPathDir + File.separator + logImageName;
        logoPathDir += File.separator + logImageName;
        File file = new File(fileName);
        try {
            img.transferTo(file);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return null;
        }
        return logoPathDir;
    }
}
