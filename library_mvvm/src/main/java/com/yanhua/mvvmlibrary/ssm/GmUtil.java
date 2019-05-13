package com.yanhua.mvvmlibrary.ssm;



/**
 * 查询统计sql拼接
 *
 * @author hsk
 */
public abstract class GmUtil {


    public static final String SM4_ECB = "ECB";

    public static final String SM4_CBC = "CBC";

    public static String encryptData(Object data,String type){
        String rtnString = "";
        String plainText = objToString(data);
        String secretKey = SM4Utils.getOrderIdByUUId();
        SM4Utils sm4 = new SM4Utils();
        sm4.secretKey = secretKey;
        if(GmUtil.SM4_ECB.equals(type)){
            rtnString = sm4.encryptData_ECB(plainText);
            rtnString += " "+secretKey;
        }else if(GmUtil.SM4_CBC.equals(type)){
            String ivKey = SM4Utils.getOrderIdByUUId();
            sm4.iv = ivKey;
            rtnString = sm4.encryptData_CBC(plainText);
            rtnString += (" "+secretKey+" "+ivKey);
        }
        return rtnString;

    }

    private static String objToString(Object data){
        String toJson = "";
        if( !(data instanceof String)){
            toJson = JsonUtil.toJson(data);
        }else{
            toJson = String.valueOf(data);
        }
        return toJson;
    }

}
