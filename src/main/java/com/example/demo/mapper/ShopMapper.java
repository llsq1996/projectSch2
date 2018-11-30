package com.example.demo.mapper;

import com.example.demo.entity.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopMapper {
    int shopAdd(@Param("shop") Shop shop);

    List<Shop> getAllShop();
}
