package com.jovi.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer id;
    private String studentNum;
    private String name;
    private String password;
    private Integer gender;  // 1-男，2-女
    private String phone;
    private Long dormitoryId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
