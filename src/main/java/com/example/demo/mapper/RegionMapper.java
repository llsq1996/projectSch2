package com.example.demo.mapper;

import com.example.demo.entity.Areas;
import com.example.demo.entity.Cities;
import com.example.demo.entity.Provinces;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface RegionMapper {
    List<Provinces> getProvince();
    List<Cities> getCity(@Param("provinceId") String provinceId);
    List<Areas> getArea(@Param("cityId") String cityid);
    String getProName(@Param("provinceId") String provinceId);
    String getCityName(@Param("cityId") String cityId);
    String getAreaName(@Param("areaId") String areaId);


}
