package com.yanhua.mvvmlibrary.binding.viewadapter.webview;

import android.databinding.BindingAdapter;
import android.text.TextUtils;

import com.tencent.smtt.sdk.WebView;
import com.yanhua.mvvmlibrary.utils.WebViewOption;

/**
 * Created by king on 2018.12.21
 */
public class ViewAdapter {

    /**
     * 加载html标签
     * @param webView
     * @param html
     */
    @BindingAdapter({"render"})
    public static void loadHtml(WebView webView, final String html) {
        if (!TextUtils.isEmpty(html)) {
            webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        }
    }

    /**
     * 加载Url
     * @param webView
     * @param url
     */
    @BindingAdapter({"loadUrl"})
    public static void loadUrl(WebView webView, final String url) {
        if (!TextUtils.isEmpty(url)) {
            WebViewOption.setOption(webView,url);
        }
    }

    /**
     *
     * @param webView
     * @param object js调用原生的方法
     */
    @BindingAdapter({"addJavascriptInterface"})
    public static void addJavascriptInterface(WebView webView, Object object){
        webView.addJavascriptInterface(object, "AndroidFun");
    }

    /**
     * 原生调用js方法
     * @param webView 控件
     * @param jsMethod 拼接方式 javascript:+方法名+参数 例如："javascript:_vue.getLoginInfo('" + 参数 + "')"
     */
    @BindingAdapter({"jsMethod"})
    public static void javascript(WebView webView, String jsMethod ){
        webView.loadUrl(jsMethod);
    }
}
