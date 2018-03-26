package com.example.demo.module.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Utils;
import com.example.demo.module.user.dao.UserDao;
import com.example.demo.module.user.entity.User;
import com.example.demo.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public JSONObject save(User user) {
        user.setSalt(String.valueOf(UUID.randomUUID()));
        user.setPassword(Utils.md5Hex(user.getPassword(), user.getSalt()));
        JSONObject json = new JSONObject();
        try {
            userDao.save(user);
            json.put("success", true);
            json.put("mag", "注册成功");
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            json.put("success", false);
            json.put("mag", "注册失败" + e.getMessage());
            return json;
        }
    }

    @Override
    public JSONObject update(User user) {
        return null;
    }

    @Override
    public JSONObject findByUsername(String customerId) {
        JSONObject json = new JSONObject();
        User user = userDao.findByUsername(customerId);
        if (user != null) {
            json.put("success", true);
            json.put("obj", user);
            return json;
        }
        json.put("success", false);
        json.put("mag", "账户不存在");
        return json;
    }
}
