package com.yanhua.mvvmlibrary.widget.address.utils;

import java.util.List;

/**
 * Created by king on 2019/01/16.
 */

public class Lists {
    public Lists() {
    }

    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    public static boolean notEmpty(List list) {
        return list != null && list.size() > 0;
    }
}
