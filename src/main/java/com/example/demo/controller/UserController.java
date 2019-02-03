package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.exEntity.UserListRec;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.ToJsonObject;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    /**
     * 添加人员
     * @param user
     * @return
     */
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

    /**
     * 获取详情
     * @param userId
     * @return
     */
    @GetMapping("/userDetail")
    @ResponseBody
    JSONObject userDetail(@Param("userId") Integer userId){
        if(Objects.nonNull(userId)){
            System.out.println(userId);
            User user=new User();
            user.setUserId(userId);
            return ToJsonObject.getSuccessJSONObject(userMapper.getUserById(user));
        }
        return ToJsonObject.getFailJSONObject(null);
    }

    /**
     * 人员列表
     * @return
     */
    @GetMapping("userList")
    @ResponseBody
    JSONObject userList(){
        List<UserListRec> userList=userMapper.getAll().stream().map(x->{
            UserListRec user=new UserListRec();
            BeanUtils.copyProperties(x,user);
            if(x.getIsAdmin()==1){
                user.setIsAdmin("有");
            }else {
                user.setIsAdmin("无");
            }
            if(x.getIsAudit()==1){
                user.setIsAudit("有");
            }else {
                user.setIsAudit("无");
            }
            return user;
        }).collect(Collectors.toList());
      return ToJsonObject.getSuccessJSONObject(userList);

    }

    /**
     * 详情编辑
     * @param user
     * @return
     */
    @PostMapping("/userUpdate")
    @ResponseBody
    JSONObject userUpdate(User user){
        if(Objects.nonNull(user)){
            System.out.println(user);
            if(1==userMapper.updateUser(user)){
                return ToJsonObject.getSuccessJSONObject();
            }
        }
        return ToJsonObject.getFailJSONObject(null);
    }

    /**
     * 删除
     * @param userId
     * @return
     */
    @GetMapping("/userDelete")
    @ResponseBody
    JSONObject userDelete(@Param("userId") Integer userId){
        if(Objects.nonNull(userId)){
            userMapper.deleteUser(userId);
            return ToJsonObject.getSuccessJSONObject();
        }
        return ToJsonObject.getFailJSONObject(null);
    }
}
