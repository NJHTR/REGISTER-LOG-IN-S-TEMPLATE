package com.example.smarthome.util;

/*
 *@Description:
 *@Date:2024/12/2 13:27
 *@Author:YPH
 *@vision:1.0
 */


public class ThreadLocalUtil {

    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    //根据键获取值
    public static <T> T get(){
        return (T) THREAD_LOCAL.get();
    }

    //存储
    public static void set(Object value) {
        THREAD_LOCAL.set(value);
    }

    //清除
    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
