package com.yanhua.mvvmlibrary.base;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.nlscan.android.scan.ScanManager;
import com.nlscan.android.scan.ScanSettings;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.yanhua.mvvmlibrary.R;
import com.yanhua.mvvmlibrary.bus.Messenger;
import com.yanhua.mvvmlibrary.bus.RxBus;
import com.yanhua.mvvmlibrary.event.InfraredEvent;
import com.yanhua.mvvmlibrary.utils.FixMemLeak;
import com.yanhua.mvvmlibrary.utils.LoadingDialogUtils;
import com.yanhua.mvvmlibrary.utils.ScanUtils;
import com.yanhua.mvvmlibrary.utils.ToastUtils;
import com.yanhua.mvvmlibrary.utils.UltimateBar;
import com.yanhua.mvvmlibrary.widget.dialog.LoadingDialog;

import java.lang.ref.SoftReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;


/**
 * Created by king on 2018.12.21
 * 一个拥有DataBinding框架的基Activity
 */
public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends RxAppCompatActivity implements IBaseActivity {
    protected V binding;
    protected VM viewModel;
    protected int viewModelId;
    protected LoadingDialog dialog;
    protected ScanManager mScanMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化红外
        initInfrared();
        //页面接受的参数方法
        initParam();
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState);
        //私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack();
        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //注册RxBus
        viewModel.registerRxBus();
        //沉浸式
        initUltimateBar(false, R.color.colorBar, 0, new SoftReference<Activity>(this));
    }

    protected void initInfrared() {
        mScanMgr = ScanManager.getInstance();

        if (mScanMgr != null) {
            mScanMgr.setOutpuMode(ScanSettings.Global.VALUE_OUT_PUT_MODE_BROADCAST);
        }

    }

    protected void registerReceiver() {
        IntentFilter intFilter = new IntentFilter(ScanManager.ACTION_SEND_SCAN_RESULT);
        intFilter.addAction(ScanUtils.ACTION_SEND_ZPD);
        intFilter.addAction(ScanUtils.ACTION_SEND_P25);
        intFilter.addAction(ScanUtils.ACTION_SEND_HONEYWELL);
        registerReceiver(mResultReceiver, intFilter);
    }

    protected void unRegisterReceiver() {
        try {
            unregisterReceiver(mResultReceiver);
        } catch (Exception e) {
        }
    }

    protected int COLOR_BLACK = 0X001;//状态栏字体为黑色
    protected int COLOR_WHITE = 0X002;//状态栏字体为白色

    protected void initUltimateBar(boolean b, int color, int alpha, SoftReference<Activity> contextSoftReference) {
        if (b) {
            UltimateBar ultimateBar = new UltimateBar(contextSoftReference.get());
            ultimateBar.setImmersionBar();
            if (barColor() == COLOR_WHITE) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);//设置状态栏字体颜色为浅色
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏字体颜色为深色
            }
        } else {
            ImmersionBar.with(this)
                    .barColor(color, alpha)
                    .init();
        }
    }

    protected int barColor() {
        return COLOR_BLACK;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除Messenger注册
        Messenger.getDefault().unregister(viewModel);
        //解除ViewModel生命周期感应
        getLifecycle().removeObserver(viewModel);
        viewModel.removeRxBus();
        viewModel = null;
        binding.unbind();
        //解决华为手机输入事件引起得内存泄漏问题
        FixMemLeak.fixLeak(new SoftReference<Activity>(this));


    }


    /**
     * 注入绑定
     */
    private void initViewDataBinding(Bundle savedInstanceState) {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState));
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        //关联ViewModel
        binding.setVariable(viewModelId, viewModel);
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding.setLifecycleOwner(this);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this);
    }

    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(viewModelId, viewModel);
        }
    }


    /**
     * =====================================================================
     **/
    //注册ViewModel与View的契约UI回调事件
    private void registorUIChangeLiveDataCallBack() {
        //加载对话框显示
        viewModel.getUC().getShowDialogEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
                showDialog(title);
            }
        });
        //加载对话框消失
        viewModel.getUC().getDismissDialogEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                dismissDialog();
            }
        });
        //跳入新页面
        viewModel.getUC().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                startActivity(clz, bundle);
            }
        });
        //跳入ContainerActivity
        viewModel.getUC().getStartContainerActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                String canonicalName = (String) params.get(BaseViewModel.ParameterField.CANONICAL_NAME);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                startContainerActivity(canonicalName, bundle);
            }
        });
        //关闭界面
        viewModel.getUC().getFinishEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                finish();
            }
        });
        //关闭上一层
        viewModel.getUC().getOnBackPressedEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                onBackPressed();
            }
        });
    }

    public void showDialog(String title) {
        if (dialog == null) {
            dialog = new LoadingDialog(this);
        }
        dialog.setMessage(title);
        dialog.setDrawable(LoadingDialogUtils.getInstance().getImage());
        dialog.showAnimation();
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dissAnimation();
        }
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivity(intent);
    }

    /**
     * =====================================================================
     **/
    @Override
    public void initParam() {

    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(Bundle savedInstanceState);

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return ViewModelProviders.of(activity).get(cls);
    }


    @Override
    public void onResume() {
        super.onResume();
        registerReceiver();

    }

    @Override
    public void onPause() {
        super.onPause();
        unRegisterReceiver();
        dismissDialog();
    }

    /**
     * 监听扫码数据的广播，当设置广播输出时作用该方法获取扫码数据
     */
    protected BroadcastReceiver mResultReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            switch (action) {
                case ScanManager.ACTION_SEND_SCAN_RESULT:
                    byte[] bvalue1 = intent.getByteArrayExtra(ScanManager.EXTRA_SCAN_RESULT_ONE_BYTES);
                    byte[] bvalue2 = intent.getByteArrayExtra(ScanManager.EXTRA_SCAN_RESULT_TWO_BYTES);
                    String svalue1 = null;
                    String svalue2 = null;
                    try {
                        if (bvalue1 != null)
                            svalue1 = new String(bvalue1, "GBK");
                        if (bvalue2 != null)
                            svalue2 = new String(bvalue1, "GBK");
                        svalue1 = svalue1 == null ? "" : svalue1;
                        svalue2 = svalue2 == null ? "" : svalue2;
                        InfraredEvent event = new InfraredEvent(1, svalue1 + "\n" + svalue2);
                        RxBus.getDefault().post(event);
                    } catch (Exception e) {
                        InfraredEvent event = new InfraredEvent(-1, "扫描失败");
                        RxBus.getDefault().post(event);
                        e.printStackTrace();
                    }
                    break;
                case ScanUtils.ACTION_SEND_ZPD:
                    String scanResult = intent.getStringExtra("data");
                    InfraredEvent event = new InfraredEvent(1, scanResult);
                    RxBus.getDefault().post(event);
                    break;

                case ScanUtils.ACTION_SEND_P25:
                    String P25_RESULT = intent.getStringExtra("BARCODE");
                    if (!P25_RESULT.isEmpty()) {
                        InfraredEvent eventP25 = new InfraredEvent(1, P25_RESULT);
                        RxBus.getDefault().post(eventP25);
                    }
                    break;

                case ScanUtils.ACTION_SEND_HONEYWELL:
                    String result = intent.getStringExtra("data");
                    if (null!=result&&!result.isEmpty()) {
                        InfraredEvent eventHoneywell = new InfraredEvent(1, result);
                        RxBus.getDefault().post(eventHoneywell);
                    }else {
                        InfraredEvent eventHoneywellFail = new InfraredEvent(-1, "扫描失败");
                        RxBus.getDefault().post(eventHoneywellFail);
                    }
                    break;
                default:
                    ToastUtils.showShort(R.string.scan_faile);
                    break;
            }

        }
    };


}
