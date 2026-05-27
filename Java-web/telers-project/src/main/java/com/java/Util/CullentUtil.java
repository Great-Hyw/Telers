package com.java.Util;


/*
 *
 * @Description:将token存入的id值存入ThreadLocal中
 * 这是一个工具类
 * */
public class CullentUtil {

    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void set(Integer id) {
        threadLocal.set(id);
    }

    public static Integer get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }

}
