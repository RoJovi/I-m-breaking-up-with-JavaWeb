package com.jovi.service.impl;

import com.jovi.mapper.DormitoryMapper;
import com.jovi.pojo.Dormitory;
import com.jovi.service.DormitoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormitoryServiceImpl implements DormitoryService {

    @Autowired
    private DormitoryMapper dormitoryMapper;

    @Override
    public List<Dormitory> list() {
        return dormitoryMapper.findAll();
    }
}
