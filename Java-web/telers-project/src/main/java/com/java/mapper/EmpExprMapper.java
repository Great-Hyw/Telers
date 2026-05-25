package com.java.mapper;

import com.java.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/*
*
* @Description: 员工工作经历Mapper
* */
@Mapper
public interface EmpExprMapper {

    void addEmpExpr(List<EmpExpr> exprList);

    void daleteEmpExpr(List<Integer> ids);



}
