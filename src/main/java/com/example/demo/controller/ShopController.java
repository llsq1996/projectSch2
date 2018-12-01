package com.example.demo.controller;

import com.example.demo.entity.Shop;
import com.example.demo.mapper.ShopMapper;
import com.example.demo.util.ToJsonObject;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ShopController {
    @Autowired
    private ShopMapper shopMapper;

    @PostMapping("/shopAdd")
    @ResponseBody
    JSONObject shopAdd(@Param("shop") Shop shop) {
        if(1==shopMapper.shopAdd(shop)){
            return ToJsonObject.getJSONObject(null, "success");
        }else{
            return ToJsonObject.getJSONObject(null, "fail");
        }
    }

    @RequestMapping("/test02")
    @ResponseBody
    JSONObject test() {
//       List<Shop> list=shopMapper.getAllShop().stream().peek(x-> System.out.println(x)).collect(Collectors.toList());
        List<Shop> list = shopMapper.getAllShop();
        for (int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));
        return ToJsonObject.getJSONObject(list, "success");
    }


}
