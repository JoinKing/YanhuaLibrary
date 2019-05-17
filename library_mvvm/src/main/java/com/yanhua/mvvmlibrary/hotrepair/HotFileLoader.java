package com.yanhua.mvvmlibrary.hotrepair;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by king on 2019/5/17.
 */
public class HotFileLoader {

    public static void copyFile(String cardFileUrl, Context context) throws IOException {

        //将下载的dex复制一份到私有目录
        File sourceFile = new File(cardFileUrl, Constants.DEX_NEME);
        File targetFile = new File(context.getDir(Constants.DEX_DIE, Context.MODE_PRIVATE).getAbsolutePath() + File.separator + Constants.DEX_NEME);//解压目录
        //如果文件存在就删除
        if (targetFile.exists()) {
            targetFile.delete();
        }
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(input);

        FileOutputStream output = new FileOutputStream(targetFile);
        BufferedOutputStream outBuff = new BufferedOutputStream(output);

        byte[] b = new byte[1024 * 5];
        int len = 0;

        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        inBuff.close();
        outBuff.close();
        output.close();
        input.close();

    }


}
