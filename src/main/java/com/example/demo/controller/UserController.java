package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.ToJsonObject;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Objects;

@Controller
public class UserController {
    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login") //get请求或post请求的映射，当地址为/test运行下面
    @ResponseBody
    JSONObject login(User user){
       if(Objects.nonNull(user)){
           System.out.println(user);
           User ob=userMapper.getUser(user);
          if(Objects.nonNull(ob)){
              return ToJsonObject.getSuccessJSONObject(ob);
          }
       }
        return ToJsonObject.getFailJSONObject2(null,"用户名或密码错误");
    }

    @PostMapping("/userAdd")
    @ResponseBody
    JSONObject userAdd(User user){
        if(Objects.nonNull(user)){
            System.out.println(user);
            if(1==userMapper.addUser(user)){
                return ToJsonObject.getSuccessJSONObject();
            }
        }
        return ToJsonObject.getFailJSONObject(null);
    }
}
