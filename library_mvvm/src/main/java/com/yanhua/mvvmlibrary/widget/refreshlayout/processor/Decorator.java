package com.yanhua.mvvmlibrary.widget.refreshlayout.processor;


import com.yanhua.mvvmlibrary.widget.refreshlayout.view.RefreshLayout;

/**
 * Created by king on 2019/1/7.
 */

public abstract class Decorator implements IDecorator {
    protected IDecorator decorator;
    protected RefreshLayout.CoContext cp;

    public Decorator(RefreshLayout.CoContext processor, IDecorator decorator1) {
        cp = processor;
        decorator = decorator1;
    }
}
