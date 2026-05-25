package com.java.service;


import com.java.pojo.PageResult;
import com.java.pojo.Stu;

public interface StudentService {
    PageResult<Stu> select(Stu stu);
}
