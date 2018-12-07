package com.example.demo.entity.exEntity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ShopRec {
    private String spName;
    private String addressList;
    private String leader;
    private String leaderPhone;
    private String cusPhone;
    private Integer category;
    private String picAddress;
    private Integer isTradeMark;
}
