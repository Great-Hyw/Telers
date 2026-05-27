package com.java.Anno;


import java.lang.annotation.*;
/*
 *
 * 注解类用来标记切入点
 *
 * */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)//该注释作用在运行时，可以利用反射拿到参数
public @interface Loganno {

}
