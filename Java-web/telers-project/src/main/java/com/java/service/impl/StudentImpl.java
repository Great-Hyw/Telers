package com.java.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.java.mapper.StudentMapper;
import com.java.pojo.PageResult;
import com.java.pojo.Stu;
import com.java.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/*
*
* @Description: 学生业务实现类
* */
@Service
public class StudentImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public PageResult<Stu> select(Stu stu) {
        PageHelper.startPage(stu.getPage(),stu.getPageSize());
        List<Stu> list = studentMapper.select(stu);
        Page<Stu> page = (Page<Stu>)list;
        return new PageResult<>(page.getTotal(), page.getResult());
    }
}
