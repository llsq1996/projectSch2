package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.ToJsonObject;
import com.google.common.collect.Lists;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@Controller
public class UserController {
    @Autowired
    private UserMapper userMapper;

    /**
     * 样例
     * @param userName
     * @return
     */
    @RequestMapping("/test")
    @ResponseBody
    JSONObject test(@Param("userName") String userName){
        User user=new User();
        user.setUserId(1);
        user.setAccount(2);
//        userMapper.addUser(user);
        System.out.println(userName);
        return ToJsonObject.getJSONObject(user,"");
    }
}
