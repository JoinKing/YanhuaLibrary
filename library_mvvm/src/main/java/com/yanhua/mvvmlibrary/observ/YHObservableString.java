package com.yanhua.mvvmlibrary.observ;

import android.databinding.ObservableField;

/**
 * A simple {@link YHObservableString} 基本数据String 保证数据不会空指针.
 * Created by WenqiangHuang
 * Creation time 2020/7/13
 * Email viphwq@163.com
 */
public class YHObservableString extends ObservableField<String> {

    public YHObservableString() {
        super("");
    }
}
