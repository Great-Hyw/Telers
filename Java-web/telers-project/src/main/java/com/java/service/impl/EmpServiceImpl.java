package com.java.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.java.mapper.EmpExprMapper;
import com.java.mapper.EmpMapper;
import com.java.pojo.Emp;
import com.java.pojo.EmpC;
import com.java.pojo.EmpExpr;
import com.java.pojo.PageResult;
import com.java.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*
*
* @Description:员工业务实现类
*
* */
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void addEmp(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.addEmp(emp);
        List<EmpExpr> exprList = emp.getExprList();
        if (exprList != null && !exprList.isEmpty()) {
            exprList.forEach(empExpr -> empExpr.setEmpId(emp.getId()));
            empExprMapper.addEmpExpr(exprList);
        }
    }

    @Override
    public PageResult<Emp> page(EmpC empC) {
        PageHelper.startPage(empC.getPage(), empC.getPageSize());
        Page<Emp> page1 = empMapper.page(empC);

        return new PageResult<Emp>(page1.getTotal(), page1.getResult());

    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void deleteEmp(List<Integer> ids) {
        empMapper.deleteEmp(ids);
        empExprMapper.daleteEmpExpr(ids);

    }

    @Override
    public Emp selectEmp(Integer id) {

        return empMapper.select(id);
    }

    @Override
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.update(emp);
        List<EmpExpr> exprList = emp.getExprList();
        empExprMapper.daleteEmpExpr(Arrays.asList(emp.getId()));
        if (emp.getExprList() != null && !emp.getExprList().isEmpty()) {
            exprList.forEach(empExpr -> empExpr.setEmpId(emp.getId()));
            empExprMapper.addEmpExpr(exprList);
        }

    }
}
