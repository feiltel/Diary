package com.nutstudio.feil.domain;

public class BaseBean <T>{
    public BaseBean(int code,String msg,T data,int count){
        this.code=code;
        this.msg=msg;
        this.data=data;
        this.count=count;
    }
    public BaseBean(int code,String msg){
        this.code=code;
        this.msg=msg;
    }
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

    private int code,count;
    private String msg;
    private T data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
