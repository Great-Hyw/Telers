package com.java.ExceptionHander;

import com.java.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/*
* 全局异常处理
*
* */
@Slf4j
@RestControllerAdvice
public class HanderEx {
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error("服务端有异常");
    }
    /*
    *
    * 添加全局处理重复添加异常
    * */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleReException(DuplicateKeyException e)
    {

        String mess=e.getMessage();
        String message = mess.substring(mess.indexOf("Duplicate entry"));
        String[] s = message.split(" ");
        log.error("服务器出现异常"+message);
        return Result.error(s[2]+"已存在");
    }
}