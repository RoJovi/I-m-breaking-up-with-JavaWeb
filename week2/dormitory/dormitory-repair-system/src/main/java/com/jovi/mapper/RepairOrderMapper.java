package com.jovi.mapper;

import com.jovi.pojo.RepairOrder;
import com.jovi.pojo.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface RepairOrderMapper {
    //创建报修单
    @Insert("INSERT INTO repair_order (order_num, student_id, dormitory_id ,type, description, image_url, create_time, update_time, status) " +
            "VALUES (#{orderNum}, #{studentId}, #{dormitoryId}, #{type}, #{description}, #{imageUrl}, #{createTime}, #{updateTime}, 0)")
    int insertRepair(RepairOrder repairOrder);

    //看自己的报修单
    @Select("SELECT * FROM repair_order WHERE student_id = #{userId}")
    List<RepairOrder> SelectRepairByStudentId(Integer userId);

    //更新状态
    @Update("UPDATE repair_order SET status = #{status}, update_time = NOW() WHERE id = #{id}")
    void updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    //显示详情
    @Select("SELECT " +
            "r.id, " +
            "r.order_num AS orderNum, " +
            "r.student_id AS studentId, " +
            "r.dormitory_id AS dormitoryId, " +
            "r.type, " +
            "r.description, " +
            "r.image_url AS imageUrl, " +
            "r.status, " +
            "r.result, " +
            "r.create_time AS createTime, " +
            "r.update_time AS updateTime, " +
            "r.admin_id AS adminId, " +
            "s.name AS studentName, " +
            "s.phone AS studentPhone, " +
            "CONCAT(d.building, '栋', d.room, '室') AS dormitoryLocation " +
            "FROM repair_order r " +
            "LEFT JOIN student s ON r.student_id = s.id " +
            "LEFT JOIN dormitory d ON s.dormitory_id = d.id " +
            "WHERE r.id = #{id}")
    Map<String, Object> SelectRepairById(@Param("id") Integer id);

    //更新管理员处理结果
    @Select("<script>" +
            "SELECT " +
            "r.id, " +
            "r.order_num as orderNum, " +
            "r.student_id as studentId, " +
            "s.name as studentName, " +
            "s.phone as studentPhone, " +
            "CONCAT(d.building, '栋', d.room, '室') as dormitoryLocation, " +
            "r.type, " +
            "r.description, " +
            "r.image_url as imageUrl, " +
            "r.status, " +
            "r.result, " +
            "r.create_time as createTime, " +
            "r.admin_id as adminId, " +
            "a.name as adminName " +
            "FROM repair_order r " +
            "LEFT JOIN student s ON r.student_id = s.id " +
            "LEFT JOIN dormitory d ON s.dormitory_id = d.id " +
            "LEFT JOIN admin a ON r.admin_id = a.id " +
            "<where>" +
            "<if test='status != null'> AND r.status = #{status}</if>" +
            "</where>" +
            "ORDER BY r.create_time DESC" +
            "</script>")
    List<Map<String, Object>> selectAllRepairs(@Param("status") Integer status);

    @Delete("DELETE FROM repair_order WHERE id = #{id}")
    void deleteRepairById(Integer id);
}
