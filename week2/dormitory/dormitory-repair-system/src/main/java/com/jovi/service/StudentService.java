package com.jovi.service;

import com.jovi.pojo.LoginStudent;
import com.jovi.pojo.Student;

import java.nio.charset.Charset;
import java.util.List;

public interface StudentService {
    LoginStudent login(Student student);

    Student getById(Integer userId);

    void update(Student student);

    void register(Student student);

    boolean checkId(String studentNum);
}
