package com.example.demo.entity;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Shop {
    private Integer id;
    private String spName;
    private String address;
    private String leader;
    private String leaderPhone;
    private String cusPhone;
    private String worker;
    private Integer category;
    private Integer delivery;
    private String picAddress;
    private Double deliPrice;
    private Double dispatch;
    private Integer result;
    private Integer valid;
    private Integer isTradeMark;
    private Integer idTradeMark;
    private Timestamp cTime;
    private Timestamp eTime;
}
