package com.example.demo.entity;

import lombok.Data;

@Data
public class TradeMark {
    private Integer id;
    private String tradeMarkName;
    private Integer shopId;
    private Integer valid;
}
