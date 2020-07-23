package com.yanhua.mvvmlibrary.ssm;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.yanhua.mvvmlibrary.utils.KLog;

import java.io.Serializable;
import java.util.List;

public class PublicGmResult implements Serializable{
    private Integer status;
    private String msg;
    private Object data;

    public static PublicGmResult success() {
        return new PublicGmResult(ResultStatus.SUCCESS.getValue(), ResultStatus.SUCCESS.getReasonPhrase(), (Object)null);
    }

    /**
     *
     * @param msg ""
     * @param data 加密的数据
     * @param type 加密方式
     * @return
     */
    public static PublicGmResult success(String msg, Object data, String type) {
        String encryptData = GmUtil.encryptData(data, type);
        return new PublicGmResult(ResultStatus.SUCCESS.getValue(), msg, encryptData);
    }


    public static PublicGmResult success(Object data, String type) {

        KLog.e(String.valueOf(data));
        String encryptData = GmUtil.encryptData(data, type);
        JsonObject object = new JsonObject();
        object.addProperty("data",encryptData);


        return new PublicGmResult(ResultStatus.SUCCESS.getValue(), ResultStatus.SUCCESS.getReasonPhrase(), object.toString());

    }

    public static PublicGmResult failed() {

        return new PublicGmResult(ResultStatus.FAILED.getValue(), ResultStatus.FAILED.getReasonPhrase(), (Object)null);

    }

    public static PublicGmResult failed(String msg, Object data, String type) {
        String encryptData = GmUtil.encryptData(data, type);
        return new PublicGmResult(ResultStatus.FAILED.getValue(), msg, encryptData);
    }

    public static PublicGmResult failed(Object data, String type) {
        String encryptData = GmUtil.encryptData(data, type);
        return new PublicGmResult(ResultStatus.FAILED.getValue(), ResultStatus.FAILED.getReasonPhrase(), encryptData);
    }

    public static PublicGmResult build(Integer status, String msg, Object data, String type) {
        String encryptData = GmUtil.encryptData(data, type);
        return new PublicGmResult(status, msg, encryptData);
    }

    public static PublicGmResult buildAddSucess() {
        return new PublicGmResult(ResultStatus.SUCCESS_ADD.getValue(), ResultStatus.SUCCESS_ADD.getReasonPhrase(), (Object)null);
    }

    public static PublicGmResult buildAddSucess(Object data, String type) {
        String encryptData = GmUtil.encryptData(data, type);
        return new PublicGmResult(ResultStatus.SUCCESS_ADD.getValue(), ResultStatus.SUCCESS_ADD.getReasonPhrase(), encryptData);
    }

    public static PublicGmResult buildDeleteSucess() {
        return new PublicGmResult(ResultStatus.SUCCESS_DELETE.getValue(), ResultStatus.SUCCESS_DELETE.getReasonPhrase(), (Object)null);
    }

    public static PublicGmResult buildDeleteSucess(Object data, String type) {
        String encryptData = GmUtil.encryptData(data, type);
        return new PublicGmResult(ResultStatus.SUCCESS_DELETE.getValue(), ResultStatus.SUCCESS_DELETE.getReasonPhrase(), encryptData);
    }

    public static PublicGmResult buildUpdateSucess() {
        return new PublicGmResult(ResultStatus.SUCCESS_UPDATE.getValue(), ResultStatus.SUCCESS_UPDATE.getReasonPhrase(), (Object)null);
    }

    public static PublicGmResult buildUpdateSucess(Object data, String type) {
        String encryptData = GmUtil.encryptData(data, type);
        return new PublicGmResult(ResultStatus.SUCCESS_UPDATE.getValue(), ResultStatus.SUCCESS_UPDATE.getReasonPhrase(), encryptData);
    }

    public static PublicGmResult buildQuerySucess() {
        return new PublicGmResult(ResultStatus.SUCCESS_QUERY.getValue(), ResultStatus.SUCCESS_QUERY.getReasonPhrase(), (Object)null);
    }

    public static PublicGmResult buildQuerySucess(Object data, String type) {
        String encryptData = GmUtil.encryptData(data, type);
        return new PublicGmResult(ResultStatus.SUCCESS_QUERY.getValue(), ResultStatus.SUCCESS_QUERY.getReasonPhrase(), encryptData);
    }

    public static PublicGmResult buildAddFailed() {
        return new PublicGmResult(ResultStatus.FAILED_ADD.getValue(), ResultStatus.FAILED_ADD.getReasonPhrase(), (Object)null);
    }

    public static PublicGmResult buildAddFailed(Object data, String type) {
        String encryptData = GmUtil.encryptData(data, type);
        return new PublicGmResult(ResultStatus.FAILED_ADD.getValue(), ResultStatus.FAILED_ADD.getReasonPhrase(), encryptData);
    }

    public static PublicGmResult buildDeleteFailed() {
        return new PublicGmResult(ResultStatus.FAILED_DELETE.getValue(), ResultStatus.FAILED_DELETE.getReasonPhrase(), (Object)null);
    }

    public static PublicGmResult buildDeleteFailed(Object data, String type) {
        String encryptData = GmUtil.encryptData(data, type);
        return new PublicGmResult(ResultStatus.FAILED_DELETE.getValue(), ResultStatus.FAILED_DELETE.getReasonPhrase(), encryptData);
    }

    public static PublicGmResult buildUpdateFailed() {
        return new PublicGmResult(ResultStatus.FAILED_UPDATE.getValue(), ResultStatus.FAILED_UPDATE.getReasonPhrase(), (Object)null);
    }

    public static PublicGmResult buildUpdateFailed(Object data, String type) {
        String encryptData = GmUtil.encryptData(data, type);
        return new PublicGmResult(ResultStatus.FAILED_UPDATE.getValue(), ResultStatus.FAILED_UPDATE.getReasonPhrase(), encryptData);
    }

    public static PublicGmResult buildQueryFailed() {
        return new PublicGmResult(ResultStatus.FAILED_QUERY.getValue(), ResultStatus.FAILED_QUERY.getReasonPhrase(), (Object)null);
    }

    public static PublicGmResult buildQueryFailed(Object data, String type) {
        String encryptData = GmUtil.encryptData(data, type);
        return new PublicGmResult(ResultStatus.FAILED_QUERY.getValue(), ResultStatus.FAILED_QUERY.getReasonPhrase(), encryptData);
    }

    public PublicGmResult() {
    }

    public PublicGmResult(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
        this.data = null;
    }

    public PublicGmResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public static PublicGmResult format(String json) {
        return (PublicGmResult) JSON.parseObject(json, PublicGmResult.class);
    }

    public static String decryCommon(String data){
        String[] decryptData = data.split(" ");
        if(decryptData.length == 0 || decryptData.length == 1 ||decryptData.length > 3){
            try {
                throw new Exception("密文格式有问题");

            } catch (Exception e) {
                e.printStackTrace();
                return data;
            }
        }
        SM4Utils sm4 = new SM4Utils();
        String decrypt = "";
        sm4.secretKey = decryptData[1];
        int l = decryptData.length;
        if(decryptData.length != 3){
            decrypt = sm4.decryptData_ECB(decryptData[0]);
        }else{
            sm4.iv = decryptData[2];
            decrypt =  sm4.decryptData_CBC(decryptData[0]);
        }
        return decrypt;
    }

    private static  String ecbCommon(String data,String secretKey)throws Exception{
        if(secretKey.isEmpty()){
            throw new Exception("加解密key不能为空");
        }
        if(data.isEmpty()){
            throw new Exception("密文不能为空");
        }
        String decryptData = "";
        SM4Utils sm4 = new SM4Utils();
        sm4.secretKey = secretKey;
        decryptData = sm4.decryptData_ECB(data);
        return decryptData;
    }
    private static  String cbcCommon(String data,String secretKey,String iv)throws Exception{
        if(secretKey.isEmpty()){
            throw new Exception("加解密key不能为空");
        }
        if(iv.isEmpty()){
            throw new Exception("ivKey不能为空");
        }
        if(data.isEmpty()){
            throw new Exception("密文不能为空");
        }
        String decryptData = "";
        SM4Utils sm4 = new SM4Utils();
        sm4.secretKey = secretKey;
        sm4.iv = iv;
        decryptData =  sm4.decryptData_CBC(data);
        return decryptData;
    }


    /**
     *
     * @param data 解密
     * @return
     * @throws
     */
    public static String  decryptData(String data){
        return decryCommon(data);
    }

    /**
     * 解密
     * @param data
     * @param clazz
     * @param <T>
     * @return
     * @throws
     */
    public static <T> T  decryptData(String data,Class<T> clazz)throws Exception{
        T t = JsonUtil.parseObject(decryCommon(data), clazz);
        return t;
    }

    public static <T> T  decryptData(Object data,Class<T> clazz)throws Exception{
        T t = JsonUtil.parseObject(decryCommon((String) data), clazz);
        return t;
    }

    public static  <T> List<T> decryptListData(String data,Class<T> clazz)throws Exception{
        List<T> list = JSON.parseArray(decryCommon(data), clazz);
        return list;
    }

    public static String hexStringToString(String s){
        if(s == null || s.equals("")){
            return null;
        }
        s = s.replace(" ","");
        byte [] baKeyWord = new byte[s.length()/2];
        for(int i = 0 ; i < baKeyWord.length; i ++){
            try {
                baKeyWord[i] = (byte)(Integer.parseInt(s.substring(i*2,i * 2 +2),16));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyWord,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new String();
        return  s;
    }


}
