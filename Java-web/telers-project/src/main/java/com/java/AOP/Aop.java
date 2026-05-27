package com.java.AOP;


import com.java.Util.CullentUtil;
import com.java.mapper.LogMapper;
import com.java.pojo.pojoLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;


/*
 *
 *AOP切面类用来记录日志
 *
 * */
@Slf4j
@Aspect
@Component
public class Aop {

    @Autowired
    private LogMapper logMapper;

    @Around("@annotation(com.java.Anno.Loganno)")
    public Object Log(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("开始记录日志");
        long l = System.currentTimeMillis();//初始的时间戳

        Object result = joinPoint.proceed();// 调用原方法

        long l1 = System.currentTimeMillis();//方法调用完后的时间戳

        Long end = l1 - l;//消耗的时长

        pojoLog pojoLog1 = new pojoLog();//创建日志对象

        log.info(joinPoint.getSignature().getName() + "花费了{}", end);
        log.info("参数为{}", joinPoint.getArgs());
        pojoLog1.setArgs(Arrays.toString(joinPoint.getArgs()));//传入参数
        pojoLog1.setUserID(CurrentId());
        pojoLog1.setMenthed(joinPoint.getSignature().getName());
        pojoLog1.setSpentTime(end);

        logMapper.insertLog(pojoLog1);

        return result;
    }

    /*
     *
     * @description: 获取当前用户ID(从当前线程的ThreadLocal里获取)
     *
     * */
    public int CurrentId() {
        return CullentUtil.get();
    }


}
