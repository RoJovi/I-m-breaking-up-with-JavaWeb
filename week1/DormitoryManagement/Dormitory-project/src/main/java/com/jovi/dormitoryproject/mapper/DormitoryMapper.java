package com.jovi.dormitoryproject.mapper;

import com.jovi.dormitoryproject.pojo.Dormitory;
import com.jovi.dormitoryproject.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DormitoryMapper {
    // 绑定宿舍（插入或更新）
    @Insert("INSERT INTO dormitory (userId, building, roomNumber, createTime, updateTime) " +
            "VALUES (#{userId}, #{building}, #{roomNumber}, NOW(), NOW()) " +
            "ON DUPLICATE KEY UPDATE " +
            "building = VALUES(building), " +
            "roomNumber = VALUES(roomNumber), " +
            "updateTime = NOW()")
    int insertOrUpdateDormitory(Dormitory dormitory);

    // 获取学生的宿舍信息
    @Select("SELECT * FROM dormitory WHERE userId = #{userId}")
    Dormitory getDormitoryByUserId(@Param("userId") String userId);

    // 删除宿舍信息
    @Delete("DELETE FROM dormitory WHERE userId = #{userId}")
    int deleteDormitory(@Param("userId") String userId);}
