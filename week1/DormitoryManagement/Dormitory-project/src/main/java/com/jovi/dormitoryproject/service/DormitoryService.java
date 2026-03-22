package com.jovi.dormitoryproject.service;

import com.jovi.dormitoryproject.mapper.DormitoryMapper;
import com.jovi.dormitoryproject.mapper.DormitoryInfoMapper;
import com.jovi.dormitoryproject.pojo.Dormitory;
import com.jovi.dormitoryproject.pojo.DormitoryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormitoryService {
    @Autowired
    private DormitoryMapper dormitoryMapper;

    @Autowired
    private DormitoryInfoMapper dormitoryInfoMapper;

    // 获取所有楼栋
    public List<String> getAllBuildings() {
        return dormitoryInfoMapper.getAllBuildings();
    }

    // 按楼栋获取宿舍
    public List<DormitoryInfo> getDormitoriesByBuilding(String building) {
        return dormitoryInfoMapper.getDormitoriesByBuilding(building);
    }

    // 绑定宿舍
    public boolean bindDormitory(String userId, String building, String roomNumber) {
        try {
            // 检查宿舍是否存在
            DormitoryInfo dormInfo = dormitoryInfoMapper.checkDormitoryExists(building, roomNumber);
            if (dormInfo == null) {
                System.out.println("该宿舍不存在！");
                return false;
            }

            // 绑定宿舍
            Dormitory dormitory = new Dormitory();
            dormitory.setUserId(userId);
            dormitory.setBuilding(building);
            dormitory.setRoomNumber(roomNumber);

            int result = dormitoryMapper.insertOrUpdateDormitory(dormitory);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Dormitory getDormitory(String userId) {
        return dormitoryMapper.getDormitoryByUserId(userId);
    }
}