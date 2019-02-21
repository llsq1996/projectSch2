package com.example.demo.entity.exEntity;

import lombok.Data;

import java.util.List;
@Data
public class Count {
    private String name;
    private Integer num;
    private List<ShopListRec> shopList;
}
