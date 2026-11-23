package com.java.service.impl;

import com.java.mapper.DeptMapper;
import com.java.pojo.Dept;
import com.java.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


/*
*
* @Description: 部门业务实现类
* */
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> findAll() {

        return deptMapper.findALL();

    }

    @Override
    public void deleteEmp(Integer id) {
        deptMapper.deleteDept(id);
    }

    @Override
    public void addDept(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());


        deptMapper.addDept(dept);
    }

    @Override
    public void UpdateDept(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }
    @Override
    public Dept goInfo(Integer id) {


        return deptMapper.goInfo(id);

    }
}
