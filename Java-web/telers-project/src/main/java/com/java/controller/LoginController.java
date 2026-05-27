package com.java.controller;


import com.java.pojo.Emp;
import com.java.pojo.Result;
import com.java.service.Login;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/*
 * 登录控制类
 * @Description: 登录控制类
 * */
@Slf4j
@RestController
public class LoginController {
    @Autowired
    private Login login;

    @PostMapping("/login")
    public Result login(@RequestBody Emp emp) {

        log.info("员工登录：{}", emp);
        Emp emp1 = login.login(emp);
        if (emp1 != null) {//登录成功
            log.info(String.valueOf(emp1));
            return Result.susses(emp1);
        }
        return Result.error("用户名或密码错误");
    }

}
