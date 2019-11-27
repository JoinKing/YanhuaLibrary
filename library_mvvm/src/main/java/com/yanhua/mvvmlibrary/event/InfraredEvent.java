package com.yanhua.mvvmlibrary.event;

/**
 * A simple {@link InfraredEvent} subclass.
 * Created by WenqiangHuang
 * Creation time 2019/11/27
 * Email viphwq@163.com
 */
public class InfraredEvent {
    public int code;
    public String data;

    public InfraredEvent(int code, String data) {
        this.code = code;
        this.data = data;
    }
}
