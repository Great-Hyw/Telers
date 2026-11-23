package com.java.mapper;


import com.github.pagehelper.Page;
import com.java.pojo.Emp;
import com.java.pojo.EmpC;
import com.java.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/*
*  员工Mapper
*
* */
@Mapper
public interface EmpMapper {

    Page<Emp> page(EmpC empC);


    void addEmp(Emp emp);


    void deleteEmp(List<Integer> ids);

    Emp select(Integer id);

    void update(Emp emp);
}
