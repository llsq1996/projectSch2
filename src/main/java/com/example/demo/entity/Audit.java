package com.example.demo.entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class Audit {
    private Integer id;
    private String name;
    private String submitter;
    private String auditor;
    private Integer idTradeMark;
    private Timestamp cTime;
    private Timestamp eTime;
    private Integer result;
    private Integer valid;
}
