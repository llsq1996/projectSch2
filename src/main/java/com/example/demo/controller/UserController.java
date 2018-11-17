package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @RequestMapping("/test")
    void test(){
        User user=new User();
        user.setUserId(1);
        user.setAccount(2);
        userMapper.addUser(user);
    }
}
