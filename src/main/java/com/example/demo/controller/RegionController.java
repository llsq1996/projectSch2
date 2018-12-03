package com.example.demo.controller;

import com.example.demo.entity.Provincial;
import com.example.demo.entity.Shop;
import com.example.demo.mapper.RegionMapper;
import com.example.demo.util.ToJsonObject;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
public class RegionController {
    @Autowired
    private RegionMapper regionMapper;
    @GetMapping("/RegionTest")
    @ResponseBody
    JSONObject getProvince(@Param("provincial") Provincial provincial) {
       List<Provincial>list=regionMapper.getProvince();
        System.out.println("aaa");
            return ToJsonObject.getJSONObject(list, "success");

    }


}
