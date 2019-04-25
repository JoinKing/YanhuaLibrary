package com.yanhua.mvvmlibrary.binding.viewadapter.viewgroup;

/**
 * Created by king on 2018.12.21
 */

import android.databinding.ViewDataBinding;

public interface IBindingItemViewModel<V extends ViewDataBinding> {
    void injecDataBinding(V binding);
}
