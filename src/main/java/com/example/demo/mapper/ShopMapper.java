package com.example.demo.mapper;

import com.example.demo.entity.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopMapper {
    int shopAdd(@Param("shop") Shop shop);
    List<Shop> getAllShop();
    List<Shop> getFasShop();
    List<Shop> getDelShop();
    List<Shop> getTruShop();
    List<Shop> getAllTruShop();
    Shop getLast();
    Shop getShopById(@Param("id") Integer id);
    int shopUpdate(@Param("shop") Shop shop);
    int updateAddress(@Param("id") Integer id,@Param("picName") String picName);
    int updateResult(@Param("id") Integer id,@Param("result") Integer result);
    int delAll(@Param("id") Integer id);

}
