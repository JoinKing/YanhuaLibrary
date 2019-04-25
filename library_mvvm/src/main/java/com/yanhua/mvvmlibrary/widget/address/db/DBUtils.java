package com.yanhua.mvvmlibrary.widget.address.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 Created by king on 2019/01/16.
 */
public class DBUtils {
    private static SQLiteDatabase db;
    //版本号
    public static final int VERSION = 1;
    //数据库名
    public static final String DB_NAME = "address.db";
    private DBUtils(Context context){
        DBOpenHelper dbHelper = new DBOpenHelper(context,DB_NAME,null, VERSION);
        db = dbHelper.getReadableDatabase();

    }
    public static SQLiteDatabase getDBInstance(Context context){
        if(db == null){
            new DBUtils(context);
        }
        return db;
    }

}
