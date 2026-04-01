package com.jovi.controller;

import com.jovi.pojo.LoginStudent;
import com.jovi.pojo.RepairOrder;
import com.jovi.pojo.Result;
import com.jovi.pojo.Student;
import com.jovi.service.RepairOrderService;
import com.jovi.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RepairOrderController {

    @Autowired
    private RepairOrderService repairOrderService;

    @Autowired
    private StudentService studentService;

    //提交报修单
    @PostMapping("/repair/submit")
    public Result SubmitRepair(@RequestBody RepairOrder repairOrder , HttpServletRequest request) {
        //获得学生id
        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null) {
            return Result.error("未登录");
        }
        //用学生id查询宿舍id
        Student student = studentService.getById(userId);
        if (student == null) {
            return Result.error("学生不存在");
        }
        repairOrder.setStudentId(userId);
        repairOrder.setDormitoryId(student.getDormitoryId());
        RepairOrder newRepair = repairOrderService.SubmitRepair(repairOrder);
        if (newRepair != null) {
            return Result.success(newRepair);
        }
        return  Result.error("提交失败，请重试");
    }

    //用人类的id查询报修列表
    @GetMapping("/repair/my-list")
    public Result getRepair(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null) {
            return Result.error("未登录");
        }

        List<RepairOrder> list = repairOrderService.getMine(userId);
        if (list.isEmpty()) {
            return Result.error("报修单不存在");
        }

        return Result.success(list);

    }


    //更新报修单状态为【取消】
    @PutMapping("/repair/{id}/cancel")
    public Result cancel(@PathVariable Integer id){

        if (id == null) {
            return Result.error("报修单不存在");
        }

        repairOrderService.cancel(id);
        return Result.success();
    }

    //admin查询所有报修列表
    @GetMapping("/admin/repairs")
    public Result getRepairs(@RequestParam(required = false) Integer status) {
        List<Map<String, Object>> repairs = repairOrderService.getAllRepairs(status);
        return Result.success(repairs);
    }


    //获取报修单详情
    @GetMapping("/admin/repair/{id}")
    public Result getRepairDetail(@PathVariable Integer id) {

        if (id == null) {
            return Result.error("报修单不存在");
        }

        Map<String, Object> detail = repairOrderService.getDetail(id);
        if (detail  == null ) {
            return Result.error("报修单不存在");
        }

        return Result.success(detail);

    }

    //更新报修单状态为【待处理】【已完成】等
    @PutMapping("/admin/repair/{id}/status")
    public Result status(@PathVariable Integer id,@RequestBody RepairOrder updateRepair){

        if (id == null) {
            return Result.error("报修单不存在");
        }

        repairOrderService.status(id,updateRepair);
        return Result.success();
    }

    //删除报修单
    @DeleteMapping("/admin/repair/{id}")
    public Result deleteRepair(@PathVariable Integer id) {

        if (id == null) {
            return Result.error("报修单不存在");
        }

        repairOrderService.delete(id);

        return Result.success();

    }
}
