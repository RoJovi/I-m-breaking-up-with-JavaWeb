package com.jovi.dormitoryproject.service;

import com.jovi.dormitoryproject.mapper.UserMapper;
import com.jovi.dormitoryproject.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public boolean register(String userId, String password, String confirmPassword, String role) {
        // 验证密码一致性
        if (!password.equals(confirmPassword)) {
            System.out.println("两次输入的密码不一致！");
            return false;
        }

        // 账号验证
        if ("student".equals(role)) {
            if (!userId.startsWith("3125") && !userId.startsWith("3225")) {
                System.out.println("学号必须以3125或3225开头！");
                return false;
            }
            if (userId.length() != 10) {
                System.out.println("学号长度必须为10位！");
                return false;
            }
        }

        try {
            // 检查账号是否已存在
            if (userMapper.checkUserExists(userId) > 0) {
                System.out.println("账号已存在！");
                return false;
            }

            // 创建用户
            User user = new User();
            user.setUserId(userId);
            user.setPassword(password);
            user.setRole(role);
            user.setName(userId);
            user.setGender('男');

            int result = userMapper.insertUser(user);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User login(String userId, String password) {
        return userMapper.login(userId, password);
    }

    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        try {
            int result = userMapper.updatePassword(userId, oldPassword, newPassword);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
