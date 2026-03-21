package com.jovi.dormitoryproject.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Dormitory {
    private Integer id;
    private String userId;      // 学生学号
    private String building;    // 楼栋
    private String roomNumber;  // 房间号
    private Date createTime;    // 创建时间
    private Date updateTime;
}
