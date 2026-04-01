package com.jovi.service.impl;

import com.jovi.mapper.RepairOrderMapper;
import com.jovi.pojo.RepairOrder;
import com.jovi.service.RepairOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RepairOrdeServicerImpl implements RepairOrderService {

    @Autowired
    private RepairOrderMapper repairOrderMapper;

    //创造新的报修单
    @Override
    public RepairOrder SubmitRepair(RepairOrder repairOrder) {
        repairOrder.setUpdateTime(LocalDateTime.now());
        repairOrder.setCreateTime(LocalDateTime.now());
        repairOrder.setOrderNum(generateOrderNum());
        if( repairOrderMapper.insertRepair(repairOrder) > 0 ){
            return repairOrder;
        }
        return null;
    }

    //根据学生id获得报修单
    @Override
    public List<RepairOrder> getMine(Integer userId) {
        return repairOrderMapper.SelectRepairByStudentId(userId);
    }

    //用报修单id更新状态为【取消】
    @Override
    public void cancel(Integer id) {
        repairOrderMapper.updateStatus(id,3);
    }

    //报修单详情
    @Override
    public Map<String, Object> getDetail(Integer id) {
        return repairOrderMapper.SelectRepairById(id);
    }

    //更改报修单状态
    @Override
    public void status(Integer id, RepairOrder updateRepair) {
        repairOrderMapper.updateStatus(id,updateRepair.getStatus());
    }


    @Override
    public List<Map<String, Object>> getAllRepairs(Integer status) {
        return repairOrderMapper.selectAllRepairs(status);
    }

    @Override
    public void delete(Integer id) {
        repairOrderMapper.deleteRepairById(id);
    }

    private String generateOrderNum() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        int random = (int) (Math.random() * 9000) + 1000;
        return "BX" + date + random;
    }
}
