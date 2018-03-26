package com.example.demo.module.user.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.module.user.entity.User;

public interface UserService {

    public JSONObject save(User user);
    public JSONObject update(User user);
    public JSONObject findByUsername(String username);
}
