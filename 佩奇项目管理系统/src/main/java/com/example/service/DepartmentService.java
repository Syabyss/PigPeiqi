package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Department;
import com.example.mapper.DepartmentMapper;
import com.example.mapper.TagMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DepartmentService extends ServiceImpl<DepartmentMapper, Department> {

    @Resource
    private DepartmentMapper departmentMapper;
}
