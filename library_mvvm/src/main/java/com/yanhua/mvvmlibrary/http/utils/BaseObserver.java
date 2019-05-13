package com.yanhua.mvvmlibrary.http.utils;


import com.yanhua.mvvmlibrary.http.BaseResponse;
import com.yanhua.mvvmlibrary.http.ExceptionHandle;
import com.yanhua.mvvmlibrary.http.NetworkUtil;
import com.yanhua.mvvmlibrary.http.ResponseThrowable;
import com.yanhua.mvvmlibrary.utils.ToastUtils;
import com.yanhua.mvvmlibrary.utils.Utils;

import io.reactivex.observers.DisposableObserver;

public abstract class BaseObserver<T> extends DisposableObserver<T> {
    @Override
    public void onNext(T t) {
        BaseResponse baseResponse = (BaseResponse) t;

        if (baseResponse.getStatus() == 100) {
            onResult((T) baseResponse);
        } else {
            onError(new Throwable("错误信息：" + baseResponse.getMessage() + ",错误码：" + baseResponse.getCode()));
        }

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ResponseThrowable) {
            onError((ResponseThrowable) e);
        } else {
            onError(new ResponseThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    /**
     * 开始加载弹窗
     */
    @Override
    public void onStart() {
        super.onStart();
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtil.isNetworkAvailable(Utils.getContext())) {
            ToastUtils.showShort("无网络，读取缓存数据");
            onComplete();
        }

    }

    /**
     * 关闭加载弹窗
     */
    @Override
    public void onComplete() {

    }


    public abstract void onError(ResponseThrowable e);


    public abstract void onResult(T t);

//    public abstract void onDeCodeResult(T w);
}
