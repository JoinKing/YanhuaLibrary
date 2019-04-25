package com.yanhua.mvvmlibrary.base;

import android.support.annotation.NonNull;

/**
 * ItemViewModel
 * Created by king on 2018.12.21
 */

public class ItemViewModel<VM extends BaseViewModel> {
    protected VM viewModel;

    public ItemViewModel(@NonNull VM viewModel) {
        this.viewModel = viewModel;
    }
}
