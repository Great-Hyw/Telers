package com.java.service;


import com.java.pojo.Clazz;
import com.java.pojo.EmpC;
import com.java.pojo.PageResult;

public interface Clazzs {

    PageResult<Clazz> selectClazzs(EmpC empC);

    void deleteClazz(Integer id);

    void addClazzs(Clazz clazz);

    PageResult<Clazz> selectAll(EmpC empC);

    void updateClazz(Clazz clazz);

    Clazz selectClazz(Integer id);
}
