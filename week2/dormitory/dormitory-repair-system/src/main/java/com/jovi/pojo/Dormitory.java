package com.jovi.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dormitory {
    private Long id;
    private String building;
    private String room;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
