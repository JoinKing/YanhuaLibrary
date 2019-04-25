package com.yanhua.mvvmlibrary.widget.address.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by king on 2019/01/16.
 */
public class AddressListView extends ListView {
    public AddressListView(Context context) {
        super(context);
    }

    public AddressListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddressListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(ev);
    }
}
