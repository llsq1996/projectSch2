package com.example.demo.mapper;

import com.example.demo.entity.Cities;
import com.example.demo.entity.Provinces;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RegionMapper {
    List<Provinces> getProvince();
    List<Cities> getCity();


}
