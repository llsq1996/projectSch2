package com.example.demo.mapper;

import com.example.demo.entity.City;
import com.example.demo.entity.Provincial;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RegionMapper {
    List<Provincial> getProvince();
    List<City> getCity();


}
