package com.java.controller;

import com.java.pojo.Dept;
import com.java.pojo.Result;
import com.java.service.DeptService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;
/*
*
* 查询所有部门
*
* */
    @RequestMapping("/depts")
    public Result SelectAll() {
        System.out.println("ssss");
        List<Dept> list = deptService.findAll();
        return Result.susses(list);
    }

/*
*
* 删除部门
*
* */
    @DeleteMapping("/depts")//删除部门
    public Result deleteEmp(Integer id) {

        deptService.deleteEmp(id);
        System.out.println(id+"a");
        return Result.susses();
    }

/*
*
* 添加部门
*
* */
    @PostMapping("/depts")//添加部门
    public Result addDept(@RequestBody Dept dept){

        deptService.addDept(dept);
        System.out.println(dept.getName());

        return Result.susses();

    }

/*
*
*
* 根据id查询部门信息
* */
    @GetMapping("/depts/{id}")
    public Result goInfo(@PathVariable Integer id){
        Dept dept=deptService.goInfo(id);
        System.out.println(dept.toString());
        return Result.susses(dept);
    }

/*
*
* 修改部门信息
*
* */
    public Result updateDept(@RequestBody Dept dept){
        deptService.UpdateDept(dept);

        return Result.susses();
    }





}
