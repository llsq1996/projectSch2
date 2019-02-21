package com.example.demo.mapper;

import com.example.demo.entity.TradeMark;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MapperScan
public interface TradeMarkMapper {
    int tradeMarkAdd(@Param("tradeMark") TradeMark tradeMark);
    List<TradeMark> getAll();
    TradeMark getById(@Param("id") Integer id);
}
