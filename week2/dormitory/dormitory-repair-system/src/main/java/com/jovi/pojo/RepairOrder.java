package com.jovi.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairOrder {
    private Long id;
    private String orderNum;
    private Integer studentId;
    private Long dormitoryId;
    private Integer type;       // 1-水电，2-家具，3-电器，4-网络，5-其他
    private String description;
    private String imageUrl;
    private Integer status;     // 0-待分配，1-处理中，2-已完成，3-已取消
    private Integer adminId;
    private String result;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
