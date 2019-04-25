package com.yanhua.mvvmlibrary.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by king on 2018/3/30.
 * 权限
 */

public class PermissionUtils {
    public static final int MY_PERMISSION_REQUEST_CODE = 10000;
    public static final String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    /**
     * 检查是否拥有指定的所有权限
     */
    public static boolean checkPermissionAllGranted(Context context) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

}
