package com.yanhua.mvvmlibrary.binding.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * A simple {@link NoScrollGridLayoutManager} subclass.
 * Created by WenqiangHuang
 * Creation time 2020/7/21
 * Email viphwq@163.com
 */
public class NoScrollGridLayoutManager extends GridLayoutManager {

    private boolean isScrollEnabled = true;

    public NoScrollGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }


    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }

}
