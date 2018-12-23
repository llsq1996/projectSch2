package com.example.demo.entity;

import lombok.Data;

@Data
public class User {
    private Integer userId;
    private String userName;
    private String password;
    private Integer isAdmin;
    private Integer isAudit;
    private Integer valid;
}
