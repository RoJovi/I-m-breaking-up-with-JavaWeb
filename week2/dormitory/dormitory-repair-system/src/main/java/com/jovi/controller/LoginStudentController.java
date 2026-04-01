package com.jovi.controller;

import com.jovi.pojo.LoginStudent;
import com.jovi.pojo.Result;
import com.jovi.pojo.Student;
import com.jovi.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/student/login")
public class LoginStudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public Result Login(@RequestBody Student student) {
        log.info("学生登录尝试: {}", student.getStudentNum());
        LoginStudent info = studentService.login(student);
        if (info != null) {
            log.info("登录成功: {}, id: {}", student.getStudentNum(), student.getId());
            return Result.success(info);
        }
        log.warn("登录失败，账号或密码错误: {}", info.getStudentNum());
        return  Result.error("用户名或密码错误");
    }
}
