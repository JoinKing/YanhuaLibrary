package com.yanhua.mvvmlibrary.http.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.yanhua.mvvmlibrary.http.cookie.CookieJarImpl;
import com.yanhua.mvvmlibrary.http.cookie.store.PersistentCookieStore;
import com.yanhua.mvvmlibrary.http.encode.ConverterFactory;
import com.yanhua.mvvmlibrary.http.interceptor.BaseInterceptor;
import com.yanhua.mvvmlibrary.http.interceptor.CacheInterceptor;
import com.yanhua.mvvmlibrary.http.interceptor.logging.Level;
import com.yanhua.mvvmlibrary.http.interceptor.logging.LoggingInterceptor;
import com.yanhua.mvvmlibrary.ssm.GmUtil;
import com.yanhua.mvvmlibrary.utils.KLog;
import com.yanhua.mvvmlibrary.utils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by king on 2018.12.21
 * RetrofitClient封装单例类, 实现网络请求
 */
public class RetrofitClient {

    //服务端根路径（默认url，使用isOtherUrl后自动默认恢复该url）
    public String baseUrl = "";
    //是否手动设置其他url
    private boolean isOtherUrl;
    private boolean DEBUG;
    private String otherUrl;
    //超时时间
    private int DEFAULT_TIMEOUT = 10;
    //缓存时间
    private int CACHE_TIMEOUT = 10 * 1024 * 1024;
    //重连次数
    private long RETRY_COUNT = 3;
    //加密规则
    public static String encryptionRule = GmUtil.SM4_CBC;

    //加密类型 1全密 2data+密文
    public static int enCodeType ;

    //微信APP_ID
    public static String APP_ID = "xx";
    //微信APP_SERECET
    public static String APP_SERECET = "xx";
    //QQ APP_ID
    public static String APP_QQID = "xx";
    //QQ APP_SERECET
    public static String APP_QQSERECET = "xx";
    //headers
    private Map<String,String> headers = new HashMap<>();

    private static Context mContext = Utils.getContext();

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    private Cache cache = null;
    private File httpCacheDirectory;

    private static RetrofitClient client;

    public static RetrofitClient builder() {
        if (null==client){
            client = new RetrofitClient();
        }
        return client;
    }

    public RetrofitClient setUrl(String url) {
        this.baseUrl = url;
        return this;
    }
    public RetrofitClient setEnCodeType(int enCodeType) {
        this.enCodeType = enCodeType;
        return this;
    }
  public RetrofitClient setEncryptionRule(String encryptionRule) {
        this.encryptionRule = encryptionRule;
        return this;
    }

    public RetrofitClient setIsOtherUrl(boolean isOtherUrl) {
        this.isOtherUrl = isOtherUrl;
        return this;
    }
    public RetrofitClient setDEBUG(boolean DEBUG) {
        this.DEBUG = DEBUG;
        return this;
    }
    public RetrofitClient setOtherUrl(String otherUrl) {
        this.otherUrl = otherUrl;
        return this;
    }

    public RetrofitClient setTimeOut(int DEFAULT_TIMEOUT) {
        this.DEFAULT_TIMEOUT = DEFAULT_TIMEOUT;
        return this;
    }

    public RetrofitClient setCacheTime(int CACHE_TIMEOUT) {
        this.CACHE_TIMEOUT = CACHE_TIMEOUT;
        return this;
    }

    public RetrofitClient setRetryCount(long RETRY_COUNT) {
        this.RETRY_COUNT = RETRY_COUNT;
        return this;
    }
    public RetrofitClient setHeaders(Map<String,String> headers) {
        this.headers = headers;
        return this;
    }

    public RetrofitClient setWxAppid(String appid){
        APP_ID = appid;
        return this;
    }
    public RetrofitClient setWxSerecet(String serecet){
        APP_SERECET = serecet;
        return this;
    }
    public RetrofitClient setQQAppid(String qqAppid){
        APP_QQID = qqAppid;
        return this;
    }
    public RetrofitClient setQQSerecet(String qqSerecet){
        APP_QQSERECET = qqSerecet;
        return this;
    }

    public RetrofitClient apply() {
        this.RetrofitClient();
        return this;
    }



    private RetrofitClient() {}


    public void RetrofitClient() {

        if (TextUtils.isEmpty(baseUrl)) {
            throw new NullPointerException("The address is not initialized");
        }
        if (null==headers){
            throw new NullPointerException("The headers is not initialized");
        }


        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(mContext.getCacheDir(), "king_cache");
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, CACHE_TIMEOUT);
            }
        } catch (Exception e) {
            KLog.e("Could not create http cache", e);
        }
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(mContext)))
                .cache(cache)
                .addInterceptor(new BaseInterceptor(headers))
                .addInterceptor(new CacheInterceptor(mContext))
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .addInterceptor(new LoggingInterceptor
                        .Builder()//构建者模式
                        .loggable(DEBUG) //是否开启日志打印
                        .setLevel(Level.BASIC) //打印的等级
                        .log(Platform.INFO) // 打印类型
                        .request("Request") // request的Tag
                        .response("Response")// Response的Tag
                        .addHeader("log-header", "I am the log request header.") // 添加打印头, 注意 key 和 value 都不能是中文
                        .build()
                )
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create()) //不需要加密放开此处
                .addConverterFactory(ConverterFactory.create(new Gson()))//加密/解密 解析器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(isOtherUrl?otherUrl:baseUrl)
                .build();


        //当请求结束后重置otherUrl
        isOtherUrl = false;
        otherUrl = null;

    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    /**
     * /**
     * execute your customer API
     * For example:
     * MyApiService service =
     * RetrofitClient.getInstance(MainActivity.this).create(MyApiService.class);
     * <p>
     * RetrofitClient.getInstance(MainActivity.this)
     * .execute(service.lgon("name", "password"), subscriber)
     * * @param subscriber
     */

    public <T> void execute(Observable<T> observable, Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(RETRY_COUNT)//请求失败重连次数
                .subscribe(subscriber);

    }

    /**
     * 设置订阅 和 所在的线程环境
     *
     *  Observable<BaseResponse<StrBean>> observable =  RetrofitClient.builder().create(ApiService.class).getText();
     *  RetrofitClient.builder().toSubscribe(observable, observer);
     */
    public <T> void toSubscribe(Observable<T> observable, BaseObserver<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(RETRY_COUNT)//请求失败重连次数
                .subscribe(subscriber);

    }
}
