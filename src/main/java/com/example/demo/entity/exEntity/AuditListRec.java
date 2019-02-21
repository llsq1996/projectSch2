package com.example.demo.entity.exEntity;

import lombok.Data;


@Data
public class AuditListRec {
    private Integer id;
    private String name;
    private String submitter;
    private String auditor;
    private String TradeMarkName;
    private String cTime;
    private String eTime;
    private String result;
    private Integer valid;
}
