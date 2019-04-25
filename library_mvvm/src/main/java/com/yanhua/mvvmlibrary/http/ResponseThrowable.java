package com.yanhua.mvvmlibrary.http;

/**
 * Created by king on 2018.12.21
 */

public class ResponseThrowable extends Exception {
    public int code;
    public String message;

    public ResponseThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
