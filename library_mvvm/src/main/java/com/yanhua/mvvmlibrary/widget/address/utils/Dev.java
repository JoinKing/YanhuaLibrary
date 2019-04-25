package com.yanhua.mvvmlibrary.widget.address.utils;

import android.content.Context;

/**
 * Created by king on 2019/01/16.
 */

public class Dev {
    public Dev() {
    }

    public static int dp2px(Context context, float dp) {
        return (int) Math.ceil((double)(context.getResources().getDisplayMetrics().density * dp));
    }
}

