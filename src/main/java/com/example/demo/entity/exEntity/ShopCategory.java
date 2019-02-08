package com.example.demo.entity.exEntity;

import lombok.Data;

import java.util.List;
@Data
public class ShopCategory {
private String categoryName;
private List<ShopListRec> shopList;
private Integer pinNum;
}
