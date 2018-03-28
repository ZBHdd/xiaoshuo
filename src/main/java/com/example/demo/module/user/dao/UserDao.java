package com.example.demo.module.user.dao;

import com.example.demo.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, Long> {

    public User findByUsername(String username);

}
