package com.java.service;


import com.java.pojo.Emp;
import com.java.pojo.EmpC;
import com.java.pojo.PageResult;

import java.util.List;

public interface EmpService {


    void addEmp(Emp emp);

    PageResult<Emp> page(EmpC empC);

    void deleteEmp(List<Integer> ids);

     Emp selectEmp(Integer id);

    void update(Emp emp);



}
