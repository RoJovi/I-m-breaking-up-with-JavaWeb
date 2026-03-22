package com.jovi.dormitoryproject.mapper;
import com.jovi.dormitoryproject.pojo.DormitoryInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

    @Mapper
    public interface DormitoryInfoMapper {

        // 获取所有宿舍
        @Select("1SELECT * FROM dormitoryInfo ORDER BY building, roomNumber")
        List<DormitoryInfo> getAllDormitories();

        // 按楼栋筛选宿舍
        @Select("SELECT * FROM dormitoryInfo WHERE building = #{building} ORDER BY roomNumber")
        List<DormitoryInfo> getDormitoriesByBuilding(@Param("building") String building);

        // 获取所有楼栋列表
        @Select("SELECT DISTINCT building FROM dormitoryInfo ORDER BY building")
        List<String> getAllBuildings();

        // 检查宿舍是否存在
        @Select("SELECT * FROM dormitoryInfo WHERE building = #{building} AND roomNumber = #{roomNumber}")
        DormitoryInfo checkDormitoryExists(@Param("building") String building, @Param("roomNumber") String roomNumber);

    }


