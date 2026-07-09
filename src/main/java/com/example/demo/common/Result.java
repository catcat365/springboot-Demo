package com.example.demo.common;

public class Result<T> {

    private int code;    // 状态码：200 表示成功
    private String msg;  // 提示信息
    private T data;      // 泛型数据：可以是 User，也可以是 List<User>

    // 私有化构造器，防止外部直接 new
    private Result() {
    }

    // 成功时调用的方法
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.msg = "操作成功";
        result.data = data;
        return result;
    }

    // 失败时调用的方法
    public static <T> Result<T> fail(String msg) {
        Result<T> result = new Result<>();
        result.code = 500;
        result.msg = msg;
        result.data = null;
        return result;
    }


    // Getter 和 Setter
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
