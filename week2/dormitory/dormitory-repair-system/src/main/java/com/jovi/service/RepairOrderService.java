package com.jovi.service;

import com.jovi.pojo.RepairOrder;
import java.util.List;
import java.util.Map;

public interface RepairOrderService {
    RepairOrder SubmitRepair(RepairOrder repairOrder);

    List<RepairOrder> getMine(Integer userId);

    void cancel(Integer id);

    Map<String, Object> getDetail(Integer id);

    void status(Integer id, RepairOrder updateRepair);

    List<Map<String, Object>> getAllRepairs(Integer status);

    void delete(Integer id);
}
