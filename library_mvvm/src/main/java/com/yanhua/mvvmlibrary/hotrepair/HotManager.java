package com.yanhua.mvvmlibrary.hotrepair;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * Created by king on 2019/5/17.
 */

public class HotManager {

    private static final String TAG = "HotManager";
    private static HashSet<File> loadedDex = new HashSet<File>();

    static {
        loadedDex.clear();
    }

    /**
     * loadDex
     * @param context
     */
    public static void loadDex(Context context) {
        if (context == null) {
            return;
        }

        File filesDir = context.getDir(Constants.DEX_DIE, Context.MODE_PRIVATE);
        File[] listFiles = filesDir.listFiles();

        for (File file : listFiles) {
            if (file.getName().startsWith(Constants.CLASSES) || file.getName().endsWith(Constants.DEX_SUFFIX)) {
                loadedDex.add(file);
            }
        }

        String optimizeDir = filesDir.getAbsolutePath() + File.separator + Constants.OPT_DEX;
        File fopt = new File(optimizeDir);

        if (!fopt.exists()) {
            fopt.mkdirs();
        }

        for (File dex : loadedDex) {

            try {
                // 系统的ClassLoader
                PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
                Class baseDexClazzLoader = Class.forName(Constants.SYS_CLASSLOADER);
                Field pathListFiled = baseDexClazzLoader.getDeclaredField(Constants.PATH_LIST);
                pathListFiled.setAccessible(true);
                Object pathListObject = pathListFiled.get(pathClassLoader);

                Class systemPathClazz = pathListObject.getClass();
                Field systemElementsField = systemPathClazz.getDeclaredField("dexElements");
                systemElementsField.setAccessible(true);
                Object systemElements = systemElementsField.get(pathListObject);

                // 自定义 ClassLoader
                DexClassLoader dexClassLoader = new DexClassLoader(dex.getAbsolutePath(), fopt.getAbsolutePath(), null, context.getClassLoader());
                Class myDexClazzLoader = Class.forName(Constants.SYS_CLASSLOADER);
                Field myPathListFiled = myDexClazzLoader.getDeclaredField(Constants.PATH_LIST);
                myPathListFiled.setAccessible(true);
                Object myPathListObject = myPathListFiled.get(dexClassLoader);

                Class myPathClazz = myPathListObject.getClass();
                Field myElementsField = myPathClazz.getDeclaredField(Constants.DEX_ELEMENTS);
                myElementsField.setAccessible(true);
                Object myElements = myElementsField.get(myPathListObject);

                // 合并数组
                Class<?> sigleElementClazz = systemElements.getClass().getComponentType();
                int systemLength = Array.getLength(systemElements);
                int myLength = Array.getLength(myElements);
                int newSystenLength = systemLength + myLength;
                // 生成一个新的 数组，类型为Element类型
                Object newElementsArray = Array.newInstance(sigleElementClazz, newSystenLength);
                for (int i = 0; i < newSystenLength; i++) {
                    if (i < myLength) {
                        Array.set(newElementsArray, i, Array.get(myElements, i));
                    } else {
                        Array.set(newElementsArray, i, Array.get(systemElements, i - myLength));
                    }
                }
                // 覆盖新数组
                Field elementsField = pathListObject.getClass().getDeclaredField(Constants.DEX_ELEMENTS);
                elementsField.setAccessible(true);
                elementsField.set(pathListObject, newElementsArray);
                Log.i(TAG, "loadDex: "+"hot successful");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
