package com.java.controller;


import com.java.pojo.PageResult;
import com.java.pojo.Result;
import com.java.pojo.Stu;
import com.java.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
*
* @Description: 对学生信息查询
* */
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public Result selectStudent(Stu stu) {

        PageResult<Stu> pageResult = studentService.select(stu);


        return Result.susses(pageResult);
    }

}
