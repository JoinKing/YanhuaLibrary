package com.yanhua.mvvmlibrary.utils;//package com.hwq.lib_common.utils;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.widget.Toast;
//
//import com.hwq.lib_common.R;
//import com.tencent.mm.opensdk.modelmsg.SendAuth;
//import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
//import com.tencent.mm.opensdk.modelmsg.WXImageObject;
//import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
//import com.tencent.mm.opensdk.modelmsg.WXTextObject;
//import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//
//
///**
// * Created by king on 2018/02/01.
// */
//
//public class WxShareUtils {
//
//    public static int WECHAT_FRIEND = 0;  //分享好友
//    public static int WECHAT_MOMENT = 1;  //分享朋友圈
//
//    public static IWXAPI api;
//
//    /**
//     * 微信登录
//     */
//    public static void WxLogin(Context context, IWXAPI api_temp) {
//        api = api_temp;
//        if (!judgeCanGo(context)) {
//            return;
//        }
//        SendAuth.Req req = new SendAuth.Req();
//        //授权域 获取用户个人信息则填写snsapi_userinfo
//        req.scope = "snsapi_userinfo";
//        //用于保持请求和回调的状态 可以任意填写
//        req.state = "test_login";
//        api.sendReq(req);
//    }
//
//    /**
//     * 分享文本至朋友圈
//     *
//     * @param text  文本内容
//     * @param judge 类型选择 好友-WECHAT_FRIEND 朋友圈-WECHAT_MOMENT
//     */
//    public static void WxTextShare(Context context, String text, int judge) {
//        if (!judgeCanGo(context)) {
//            return;
//        }
//        //初始化WXTextObject对象，填写对应分享的文本内容
//        WXTextObject textObject = new WXTextObject();
//        textObject.text = text;
//        //初始化WXMediaMessage消息对象，
//        WXMediaMessage message = new WXMediaMessage();
//        message.mediaObject = textObject;
//        message.description = text;
//        //构建一个Req请求对象
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = String.valueOf(System.currentTimeMillis());   //transaction用于标识请求
//        req.message = message;
//        req.scene = judge;      //分享类型 好友==0 朋友圈==1
//        //发送请求
//        api.sendReq(req);
//    }
//
//    /**
//     * 分享图片
//     *
//     * @param bitmap 图片bitmap,建议别超过32k
//     * @param judge  类型选择 好友-WECHAT_FRIEND 朋友圈-WECHAT_MOMENT
//     */
//    public static void WxBitmapShare(Context context, Bitmap bitmap, int judge) {
//        if (!judgeCanGo(context)) {
//            return;
//        }
//        WXImageObject wxImageObject = new WXImageObject(bitmap);
//        WXMediaMessage message = new WXMediaMessage();
//        message.mediaObject = wxImageObject;
//
//        Bitmap thunmpBmp = Bitmap.createScaledBitmap(bitmap, 50, 50, true);
//        bitmap.recycle();
//        message.thumbData = ImageUtil.bmpToByteArray(thunmpBmp, true);
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = String.valueOf(System.currentTimeMillis());
//        req.message = message;
//        req.scene = judge;
//        api.sendReq(req);
//    }
//
//    /**
//     * 分享网址到微信、朋友圈
//     */
//    public static void shareUrlToWechat(Context context, int flag, String url, String title, String info) {
//        if (!judgeCanGo(context)) {
//            return;
//        }
//        //flag 1是朋友圈，0是好友，
//        WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = url;
//        WXMediaMessage msg = new WXMediaMessage(webpage);
//
//        msg.title = title;
//        msg.description = info;
//        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.error_image);
//        msg.setThumbImage(thumb);
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = String.valueOf(System.currentTimeMillis());
//        req.message = msg;
//        req.scene = flag;
//        api.sendReq(req);
//    }
//
//    /**
//     * 检查是否安装微信
//     *
//     * @param context
//     * @return
//     */
//    public static boolean judgeCanGo(Context context) {
//        if (null == api) {
//            Toast.makeText(context, "Please initialize WeChat", Toast.LENGTH_SHORT).show();
//            throw new NullPointerException("Please initialize WeChat");
//        }
//        if (!api.isWXAppInstalled()) {
//            Toast.makeText(context, "请先安装微信应用", Toast.LENGTH_SHORT).show();
//            return false;
//        } else if (!api.isWXAppInstalled()) {
//            Toast.makeText(context, "请先更新微信应用", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        return true;
//    }
//
//}
