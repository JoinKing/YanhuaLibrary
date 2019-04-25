package com.yanhua.mvvmlibrary.binding.viewadapter.spinner;

/**
 * Created by king on 2018.12.21
 * 下拉Spinner控件的键值对, 实现该接口,返回key,value值, 在xml绑定List<IKeyAndValue>
 */
public interface IKeyAndValue {
    String getKey();

    String getValue();
}
