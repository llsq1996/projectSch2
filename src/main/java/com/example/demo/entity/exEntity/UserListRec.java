package com.example.demo.entity.exEntity;

import lombok.Data;

@Data
public class UserListRec {
    private Integer userId;
    private String userName;
    private String password;
    private String isAdmin;
    private String isAudit;
    private Integer valid;
}
