package com.java.service;


import com.java.pojo.Dept;

import java.util.List;


public interface DeptService {

    List<Dept> findAll();

    void deleteEmp(Integer id);

    void addDept(Dept dept);

    void UpdateDept(Dept dept);

    Dept goInfo(Integer id);
}
