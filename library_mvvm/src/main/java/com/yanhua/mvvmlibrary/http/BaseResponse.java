package com.yanhua.mvvmlibrary.http;

import java.io.Serializable;

/**
 * Created by king on 2018.12.21
 * 该类仅供参考，实际业务返回的固定字段, 根据需求来定义，
 */
public class BaseResponse<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return data;
    }

    public void setResult(T data) {
        this.data = data;
    }

    public boolean isOk() {
        return code == 0;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }
}
