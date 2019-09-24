package com.yanhua.mvvmlibrary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanhua.mvvmlibrary.R;


/**
 * A simple {@link LoadingDialog} subclass.
 * Created by WenqiangHuang
 * Creation time 2019/8/26
 * Email viphwq@163.com
 */
public class LoadingDialog extends Dialog {

    private ImageView imLoad;
    private Context context;
    private Animation drawable;
    private TextView tvTitle;
    private String message = "";

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.bottom_dialog);
        this.context = context;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        setCancelable(false);
        imLoad = findViewById(R.id.imLoad);
        tvTitle = findViewById(R.id.tvTitle);
        drawable = AnimationUtils.loadAnimation(context,R.anim.dialog_loading);
        imLoad.startAnimation(drawable);
        if (!message.isEmpty()){
            tvTitle.setText(message);
        }
    }


}