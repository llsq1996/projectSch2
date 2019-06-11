package com.example.demo.mapper;

import com.example.demo.entity.TradeMark;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Mapper
public interface TradeMarkMapper {
    int tradeMarkAdd(@Param("tradeMark") TradeMark tradeMark);
    List<TradeMark> getAll();
    TradeMark getById(@Param("id") Integer id);
    int updateShopId(@Param("id") Integer id,@Param("shopId") Integer shopId);
}
