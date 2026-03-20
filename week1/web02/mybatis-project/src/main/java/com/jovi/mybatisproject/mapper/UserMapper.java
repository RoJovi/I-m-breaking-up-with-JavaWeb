package com.jovi.mybatisproject.mapper;

import com.jovi.mybatisproject.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from users")
    public List<User> findAll();

}
