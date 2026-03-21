package com.jovi.dormitoryproject.service;

import com.jovi.dormitoryproject.mapper.DormitoryMapper;
import com.jovi.dormitoryproject.mapper.UserMapper;
import com.jovi.dormitoryproject.pojo.Dormitory;
import com.jovi.dormitoryproject.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DormitoryService {
    @Autowired
    private DormitoryMapper dormitoryMapper;

    public boolean bindDormitory(String userId, String building, String roomNumber) {
        try {
            Dormitory dormitory = new Dormitory();
            dormitory.setUserId(userId);
            dormitory.setBuilding(building);
            dormitory.setRoomNumber(roomNumber);

            int result = dormitoryMapper.insertOrUpdateDormitory(dormitory);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("该宿舍不存在喵");
            return false;
        }
    }

    public Dormitory getDormitory(String userId) {
        return dormitoryMapper.getDormitoryByUserId(userId);
    }
}
