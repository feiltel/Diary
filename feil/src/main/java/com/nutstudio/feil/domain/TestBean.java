package com.nutstudio.feil.domain;

public class TestBean <T>{
    public TestBean(int code,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private T data;
}
