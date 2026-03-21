package com.jovi.dormitoryproject.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Repair {
    private Integer id;
    private String orderId;          // 报修单号
    private String userId;           // 报修人学号
    private String equipment;    // 设备类型
    private String description;      // 问题描述
    private String status;           // 状态：待处理、处理中、已完成
    private String building;         // 楼栋
    private String roomNumber;       // 房间号
    private Date createTime;         // 创建时间
    private Date updateTime;
}
