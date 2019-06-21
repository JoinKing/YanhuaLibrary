package com.yanhua.mvvmlibrary.http.utils;


import com.yanhua.mvvmlibrary.http.BaseResponse;
import com.yanhua.mvvmlibrary.http.ExceptionHandle;
import com.yanhua.mvvmlibrary.http.NetworkUtil;
import com.yanhua.mvvmlibrary.http.ResponseThrowable;
import com.yanhua.mvvmlibrary.utils.ToastUtils;
import com.yanhua.mvvmlibrary.utils.Utils;

import io.reactivex.observers.DisposableObserver;

public abstract class BaseObserver<T> extends DisposableObserver<T> {


    private static final int SUCCESS = 100;//操作成功
    private static final int SUCCESS_ADD = 101;//新增成功
    private static final int SUCCESS_DELETE = 102;//删除成功
    private static final int SUCCESS_UPDATE = 103;//修改成功
    private static final int SUCCESS_QUERY = 104;//查询成功

    private static final int FAILED = 200;//操作失败
    private static final int FAILED_ADD = 201;//新增失败
    private static final int FAILED_DELETE = 202;//删除失败
    private static final int FAILED_UPDATE = 203;//更新失败
    private static final int FAILED_QUERY = 204;//查询失败

    @Override
    public void onNext(T t) {
        BaseResponse baseResponse = (BaseResponse) t;
        int status = baseResponse.getStatus();

        if (status == SUCCESS||status==SUCCESS_ADD||status==SUCCESS_DELETE||status ==SUCCESS_UPDATE||status==SUCCESS_QUERY) {
            onResult((T) baseResponse);
        } else {
            onError(new ResponseThrowable(new Throwable(baseResponse.getMessage()), ExceptionHandle.ERROR.UNKNOWN));
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

}
