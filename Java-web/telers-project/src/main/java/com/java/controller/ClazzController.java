package com.java.controller;

import com.java.pojo.Clazz;
import com.java.pojo.EmpC;
import com.java.pojo.PageResult;
import com.java.pojo.Result;
import com.java.service.Clazzs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/*
 *
 * 对班级的操作功能
 *
 * */
@RequestMapping("/clazzs")
@RestController
public class ClazzController {
    @Autowired
    private Clazzs clazzs;

    /**
     *
     * 查询班级
     *
     */
    @GetMapping
    public Result SelectClazz(EmpC empC) {

        PageResult<Clazz> clazzPageResult = clazzs.selectClazzs(empC);

        return Result.susses(clazzPageResult);
    }

    /*
     *
     * 根据id删除班级
     *
     * */
    @DeleteMapping("/{id}")
    public Result deleteClazz(@PathVariable("id") Integer id) {

        clazzs.deleteClazz(id);

        return Result.susses();
    }
/*
*
*根据id查询班级
*
* */
    @GetMapping("/{id}")
    public Result selectClazz(@PathVariable("id") Integer id) {

        Clazz clazz = clazzs.selectClazz(id);

        return Result.susses(clazz);
    }
/*
*
* 添加班级
*
* */
    @PostMapping
    public Result addClazzs(@RequestBody Clazz clazz) {

        clazzs.addClazzs(clazz);


        return Result.susses();
    }

    /*
    *
    * 查询所有班级
    * */
    @GetMapping("/list")
    public Result SelectAll(EmpC empC) {
        PageResult<Clazz> clazzPageResult = clazzs.selectAll(empC);
        return Result.susses(clazzPageResult);
    }
/*
*
* 修改班级
*
* */
    @PutMapping
    public Result updateClazz(@RequestBody Clazz clazz) {
        clazzs.updateClazz(clazz);
        return Result.susses();
    }
}
