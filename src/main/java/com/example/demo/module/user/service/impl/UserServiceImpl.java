package com.example.demo.module.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.module.user.dao.UserDao;
import com.example.demo.module.user.entity.User;
import com.example.demo.module.user.service.UserService;
import com.example.demo.util.ResultJson;
import com.example.demo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public ResultJson save(User user) {
        user.setSalt(String.valueOf(UUID.randomUUID()));
        user.setPassword(Utils.md5Hex(user.getPassword(), user.getSalt()));
        try {
            userDao.save(user);
            return new ResultJson().setSuccess(true).setMsg("注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultJson().setSuccess(false).setMsg("注册失败" + e.getMessage());
        }
    }

    @Override
    public JSONObject update(User user) {
        return null;
    }

    @Override
    public ResultJson findByUsername(String customerId) {
        User user = userDao.findByUsername(customerId);
        if (user != null) {
            return new ResultJson().setSuccess(true).setObj(user);
        }
        return new ResultJson().setSuccess(false).setMsg("账户不存在");
    }
}
