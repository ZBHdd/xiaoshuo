package com.example.demo.module.user.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.module.user.entity.User;
import com.example.demo.util.ResultJson;

public interface UserService {

    public ResultJson save(User user);
    public JSONObject update(User user);
    public ResultJson findByUsername(String username);
}
