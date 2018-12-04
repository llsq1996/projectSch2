package com.example.demo.controller;


import com.example.demo.entity.Areas;
import com.example.demo.entity.Cities;
import com.example.demo.entity.Provinces;
import com.example.demo.mapper.RegionMapper;
import com.example.demo.util.ToJsonObject;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
public class RegionController {
    @Autowired
    private RegionMapper regionMapper;
    @GetMapping("/RegionTest")
    @ResponseBody
    JSONObject getProvince(@Param("provinces") Provinces provinces) {
       List<Provinces>list=regionMapper.getProvince();
        System.out.println("aaa");
            return ToJsonObject.getJSONObject(list, "success",1);

    }
    @GetMapping("/RegionTest1")
    @ResponseBody
    JSONObject getCity(@Param("provinceId") String provinceId) {
        System.out.println(provinceId);
        List<Cities>list=regionMapper.getCity(provinceId);
        System.out.println(list);
        System.out.println("aaa");
        return ToJsonObject.getJSONObject(list, "success",1);

    }
    @GetMapping("/RegionTest2")
    @ResponseBody
    JSONObject getArea(@Param("cityId") String cityId) {
        System.out.println(cityId);
        List<Areas>list=regionMapper.getArea(cityId);
        System.out.println(list);
        System.out.println("aaa");
        return ToJsonObject.getJSONObject(list, "success",1);

    }


}
