package com.nutdiary.diary.base;

/**
 * Author:Administrator
 * Time:2019/1/31 0031
 * 通用的返回体
 */
public class ResultBean{

    private int code;
    private String msg;

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
}
