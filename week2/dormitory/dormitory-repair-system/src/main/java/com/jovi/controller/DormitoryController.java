package com.jovi.controller;

import com.jovi.pojo.Dormitory;
import com.jovi.pojo.Result;
import com.jovi.pojo.Student;
import com.jovi.service.DormitoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dormitory")
public class DormitoryController {

    @Autowired
    private DormitoryService dormitoryService;

    //获取宿舍列表
    @GetMapping("/list")
    public Result getDormitoryList() {
        List<Dormitory> Dormitorylist = dormitoryService.list();
        return Result.success(Dormitorylist);
    }


}
