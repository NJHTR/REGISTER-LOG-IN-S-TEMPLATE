package com.example.smarthome.entity;


public class Result<T> {
    private Integer code;//状态码: 0-成功,1-失败
    private String message;//提示信息
    private T date;//响应数据

    public static <E> Result<E> success(E data){
        return new Result<>(0,"操作成功",data);
    }

    public static Result success(){
        return new Result(0,"操作成功",null);
    }

    public static Result success(String message){
        return new Result(0,message,null);
    }

    public static Result error(){
        return new Result(1,"操作失败",null);
    }

    public static Result error(String message){
        return new Result(1,message,null);
    }

    public Result(Integer code, String message, T date) {
        this.code = code;
        this.message = message;
        this.date = date;
    }

    public Result() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getDate() {
        return date;
    }

    public void setDate(T date) {
        this.date = date;
    }
}
