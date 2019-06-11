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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
public class RegionController {
    @Autowired
    private RegionMapper regionMapper;

    /**
     * 获取省份
     * @return
     */
    @GetMapping("/getProvince")
    @ResponseBody
    JSONObject getProvince() {
       List<Provinces>list=regionMapper.getProvince();
            return ToJsonObject.getSuccessJSONObject(list);
    }

    /**
     * 获取城市
     * @param provinceId
     * @return
     */
    @GetMapping("/getCity")
    @ResponseBody
    JSONObject getCity(@Param("provinceId") String provinceId) {
        List<Cities>list=regionMapper.getCity(provinceId);
        return ToJsonObject.getSuccessJSONObject(list);

    }

    /**
     * 获取地区
     * @param cityId
     * @return
     */
    @GetMapping("/getArea")
    @ResponseBody
    JSONObject getArea(@Param("cityId") String cityId) {
        List<Areas>list=regionMapper.getArea(cityId);
        return ToJsonObject.getSuccessJSONObject(list);
    }
    @PostMapping("/test123")
    @ResponseBody
    String Test(@RequestBody List<Areas> areas){
        System.out.println(areas.toString());
        return areas.toString();
    }


}
