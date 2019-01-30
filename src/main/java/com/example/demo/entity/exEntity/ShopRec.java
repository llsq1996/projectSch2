package com.example.demo.entity.exEntity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ShopRec {
    private Integer id;
    private String spName;
    private String addressList;
    private String leader;
    private String leaderPhone;
    private String cusPhone;
    private String worker;
    private Integer category;
    private Integer delivery;
    private String picAddress;
    private String deliPrice;
    private String dispatch;
    private Integer isTradeMark;
}
