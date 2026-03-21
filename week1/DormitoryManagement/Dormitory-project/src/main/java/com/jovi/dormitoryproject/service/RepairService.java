package com.jovi.dormitoryproject.service;

import com.jovi.dormitoryproject.mapper.DormitoryMapper;
import com.jovi.dormitoryproject.mapper.RepairMapper;
import com.jovi.dormitoryproject.pojo.Dormitory;
import com.jovi.dormitoryproject.pojo.Repair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairService {
    @Autowired
    private RepairMapper repairMapper;

    // 生成报修单号
    private String generateOrderId() {
        return "RE" + System.currentTimeMillis();
    }

    public Repair createOrder(String userId, String equipment,
                                   String description, String building, String roomNumber) {
        try {
            Repair order = new Repair();
            order.setOrderId(generateOrderId());
            order.setUserId(userId);
            order.setEquipment(equipment);
            order.setDescription(description);
            order.setBuilding(building);
            order.setRoomNumber(roomNumber);
            order.setStatus("待处理");

            int result = repairMapper.insertRepairOrder(order);
            if (result > 0) {
                return order;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Repair> getUserOrders(String userId) {
        return repairMapper.getOrdersByUserId(userId);
    }

    public List<Repair> getAllOrders() {
        return repairMapper.getAllOrders();
    }

    public List<Repair> getOrdersByStatus(String status) {
        return repairMapper.getOrdersByStatus(status);
    }

    public Repair getOrderDetail(String orderId) {
        return repairMapper.getOrderByOrderId(orderId);
    }

    public boolean updateOrderStatus(String orderId, String status) {
        try {
            int result = repairMapper.updateOrderStatus(orderId, status);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteOrder(String orderId) {
        try {
            int result = repairMapper.deleteOrder(orderId);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelOrder(String orderId, String userId) {
        try {
            int result = repairMapper.cancelOrder(orderId, userId, "待处理");
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
