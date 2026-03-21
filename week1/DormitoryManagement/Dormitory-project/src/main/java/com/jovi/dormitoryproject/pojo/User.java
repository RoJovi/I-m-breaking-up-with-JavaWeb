package com.jovi.dormitoryproject.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;
    private String userId;      // 学号/工号
    private String password;
    private String role;        // 角色：student/admin
    private String name;
    private char gender;
    private Date createTime;    // 创建时间
}
