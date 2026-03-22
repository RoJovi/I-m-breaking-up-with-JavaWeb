package com.jovi.dormitoryproject.mapper;

import com.jovi.dormitoryproject.pojo.Dormitory;
import com.jovi.dormitoryproject.pojo.Repair;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RepairMapper {
    // 创建报修单
    @Insert("INSERT INTO repair (orderId, userId, equipment, description, " +
            "status, building, roomNumber, createTime, updateTime) " +
            "VALUES (#{orderId}, #{userId}, #{equipment}, #{description}, " +
            "#{status}, #{building}, #{roomNumber}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertRepairOrder(Repair repairOrder);

    // 获取用户的报修记录
    @Select("SELECT * FROM repair WHERE userId = #{userId} ORDER BY createTime DESC")
    List<Repair> getOrdersByUserId(@Param("userId") String userId);

    // 获取所有报修单
    @Select("SELECT * FROM repair ORDER BY createTime DESC")
    List<Repair> getAllOrders();

    // 按状态筛选
    @Select("SELECT * FROM repair WHERE status = #{status} ORDER BY createTime DESC")
    List<Repair> getOrdersByStatus(@Param("status") String status);

    // 获取单个报修单
    @Select("SELECT * FROM repair WHERE orderId = #{orderId}")
    Repair getOrderByOrderId(@Param("orderId") String orderId);

    // 管理员更新报修单状态
    @Update("UPDATE repair SET status = #{status}, updateTime = NOW() " +
            "WHERE orderId = #{orderId}")
    int updateOrderStatus(@Param("orderId") String orderId,
                          @Param("status") String status);

    // 管理员删除报修单
    @Delete("DELETE FROM repair WHERE orderId = #{orderId}")
    int deleteOrder(@Param("orderId") String orderId);

    // 学生取消报修单
    @Delete("DELETE FROM repair WHERE orderId = #{orderId} " +
            "AND userId = #{userId} AND status = #{status}")
    int cancelOrder(@Param("orderId") String orderId,
                    @Param("userId") String userId,
                    @Param("status") String status);
}
