package com.jovi.dormitoryproject.mapper;

import com.jovi.dormitoryproject.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    // 检查用户是否存在
    @Select("SELECT COUNT(*) FROM `user` WHERE userId = #{userId}")
    int checkUserExists(@Param("userId") String userId);

    // 注册
    @Insert("INSERT INTO `user` (userId, password, role, name, gender, createTime) " +
            "VALUES (#{userId}, #{password}, #{role}, #{name}, #{gender}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);

    // 登录验证
    @Select("SELECT * FROM `user` WHERE userId = #{userId} AND password = #{password}")
    User login(@Param("userId") String userId, @Param("password") String password);

    // 修改密码
    @Update("UPDATE `user` SET password = #{newPassword} " +
            "WHERE userId = #{userId} AND password = #{oldPassword}")
    int updatePassword(@Param("userId") String userId,
                       @Param("oldPassword") String oldPassword,
                       @Param("newPassword") String newPassword);

    // 根据用户ID获取用户
    @Select("SELECT * FROM `user` WHERE userId = #{userId}")
    User getUserByUserId(@Param("userId") String userId);

    // 获取所有用户
    @Select("SELECT * FROM `user` ORDER BY createTime DESC")
    List<User> getAllUsers();}
