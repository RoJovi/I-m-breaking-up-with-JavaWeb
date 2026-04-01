package com.jovi.service.impl;

import com.jovi.mapper.StudentMapper;
import com.jovi.pojo.LoginStudent;
import com.jovi.pojo.Result;
import com.jovi.pojo.Student;
import com.jovi.service.StudentService;
import com.jovi.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    //登录
    @Override
    public LoginStudent login(Student student) {
        Student s = studentMapper.selectByStudentNumAndPassword(student);

        //token自定义信息
        Map<String,Object> dataMap = new HashMap<>();	//Map集合
        dataMap.put("userId",s.getId());
        dataMap.put("studentNum",s.getStudentNum());
        dataMap.put("userType", 0);            //学生登录标记
        String jwt = JwtUtils.generateToken(dataMap);

        if(s != null) return new LoginStudent(s.getId(), s.getStudentNum(), s.getName(), jwt);
        return null;
    }

    //查询学生信息
    @Override
    public Student getById(Integer Id) {
        return studentMapper.selectById(Id);
    }

    //修改基本信息
    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.update(student);
    }

    @Override
    public void register(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        student.setCreateTime(LocalDateTime.now());
        studentMapper.insertNumAndPassword(student);
    }

    @Override
    public boolean checkId(String studentNum) {
        int find = studentMapper.SelectByStudentNum(studentNum);
        return (find != 0);
    }

}
