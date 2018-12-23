package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.ToJsonObject;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    private UserMapper userMapper;

    /**
     * 样例
     * @param userName
     * @return
     */
    @RequestMapping("/test") //get请求或post请求的映射，当地址为/test运行下面
    @ResponseBody
    JSONObject test(@Param("userName") String userName){
        User user=new User();
        user.setUserId(1);
        user.setUserName("刘家树");
        user.setIsAdmin(1);
        user.setIsAudit(1);
        user.setValid(1);
        userMapper.addUser(user);
        System.out.println(userName);
        return ToJsonObject.getSuccessJSONObject(user);
    }
}
