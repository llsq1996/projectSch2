package com.example.demo.mapper;

import com.example.demo.entity.Audit;
import com.example.demo.entity.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Mapper
public interface AuditMapper {
    int shopAdd(@Param("shop") Audit shop);
    List<Audit> getAllShop();
    List<Audit> getAllAuditShop();
    Audit getById(@Param("id") Integer id);
    Audit getByIdAll(@Param("id") Integer id);
    int updateStatus(@Param("id") Integer id);
}
