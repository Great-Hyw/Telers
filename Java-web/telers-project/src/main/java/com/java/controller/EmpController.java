package com.java.controller;

import com.java.pojo.Emp;
import com.java.pojo.EmpC;
import com.java.pojo.PageResult;
import com.java.pojo.Result;
import com.java.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import java.util.List;



/*
*
* 对员工管理模块的增删改查
*
* */
@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    private EmpService empService;
    @Autowired
    private HandlerMapping resourceHandlerMapping;

    /*
    *
    * 查询员工
    *
    * */
    @GetMapping
    public Result page(EmpC empC) {

        log.info("开始页数为：{},页数{}", empC.getPage(), empC.getPageSize());
        PageResult<Emp> page1 = empService.page(empC);


        return Result.susses(page1);
    }
/*
*
* 添加员工
*
* */
    @PostMapping
    public Result addEmp(@RequestBody Emp emp) {

        empService.addEmp(emp);

        return Result.susses();

    }
/*
*
* 删除员工
*
* */
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {

        log.info("接收所要删除的员工id：{}", ids);
        empService.deleteEmp(ids);


        return Result.susses();
    }

/*
*
* 根据id查询员工
*
* */
    public Result select(@PathVariable Integer id){

        log.info("根据id查询员工：{}",id);

        Emp emp= empService.selectEmp(id);
        log.info("查询结果：{}",emp);
        return Result.susses(emp);
    }
/*
*
* 修改员工信息
*
* */
    @PutMapping
    public Result updateEmp(@RequestBody Emp emp){

        empService.update(emp);
        log.info("修改员工信息：{}",emp);
        return Result.susses();
    }

}
