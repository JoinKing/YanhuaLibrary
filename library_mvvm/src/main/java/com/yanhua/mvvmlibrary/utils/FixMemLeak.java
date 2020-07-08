package com.yanhua.mvvmlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;

/**
 *
 *
 * 针对华为手机InputMethodManager内存泄漏问题
 */
public class FixMemLeak {

    private static Field field;
    private static boolean hasField = true;

    public static void fixLeak(SoftReference<Activity> context) {
        if (!hasField) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.get().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mLastSrvView"};

        for (String param : arr) {
            try {
                if (field == null) {
                    field = imm.getClass().getDeclaredField(param);
                }
                if (field == null) {
                    hasField = false;
                }
                if (field != null) {
                    field.setAccessible(true);
                    field.set(imm, null);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}