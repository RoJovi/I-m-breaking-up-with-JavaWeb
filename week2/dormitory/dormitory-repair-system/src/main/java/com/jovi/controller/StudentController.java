package com.jovi.controller;

import com.jovi.pojo.Admin;
import com.jovi.pojo.Result;
import com.jovi.pojo.Student;
import com.jovi.service.AdminService;
import com.jovi.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    //注册
    @PostMapping("register")
    public Result register(@RequestBody Student student) {

        //先检查有没有注册过
        if(studentService.checkId(student.getStudentNum())){
            return Result.error("账号已存在");
        }

        studentService.register(student);
        return Result.success();
    }

    //用id查询学生信息
    @GetMapping("info")
    public Result getInfo(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null) {
            return Result.error("未登录");
        }

        Student student = studentService.getById(userId);
        if (student == null) {
            return Result.error("用户不存在");
        }

        // 返回时去掉密码
        student.setPassword(null);
        return Result.success(student);

    }


    //更新基本信息
    @PutMapping("info")
    public Result update(@RequestBody Student student, HttpServletRequest request) {
        //获得学生id
        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null) {
            return Result.error("未登录");
        }
        student.setId(userId);
        if (student == null) {
            return Result.error("用户不存在");
        }

        studentService.update(student);
        return Result.success();
    }
}
