package com.yanhua.mvvmlibrary.ssm;

import com.alibaba.fastjson.JSON;

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
        String encryptData = GmUtil.encryptData(data, type);
        return new PublicGmResult(ResultStatus.SUCCESS.getValue(), ResultStatus.SUCCESS.getReasonPhrase(), encryptData);
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
     * @throws Exception
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
     * @throws Exception
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

    public static void main(String args[])throws Exception{

        String str =
        "[{'id':'1233','name':'asdasda'}]";
        PublicGmResult ecb = PublicGmResult.buildQuerySucess(str, "CBC");
        String ss = (String)ecb.getData();
        System.out.println(ss);

/*        //                                     uBb7PWLGXiDjzoU2qE9bHMdlIJm8hxydPSaToMWr3/gM2iVRFVxCA2frph5kPEuEITdPA9lxqC+he80upgjwhQ== 85yyle0m85yyle0m 88x5ethm88x5ethm
                                            // uBb7PWLGXiDjzoU2qE9bHMdlIJm8hxydPSaToMWr3/gM2iVRFVxCA2frph5kPEuEITdPA9lxqC\ he80upgjwhQ== 85yyle0m85yyle0m 88x5ethm88x5ethm


     *//**//*   String str  = "7542623750574c475869446a7a6f553271453962484d646c494a6d3868787964505361546f4d5772332f674d3269565246567843413266727068356b504575454954645041396c7871432b686538307570676a7768513d3d20383579796c65306d383579796c65306d20383878356574686d383878356574686d";
        System.out.println(hexStringToString(str));
        String s = PublicGmResult.decryptData(hexStringToString(str));
        System.out.println(s);*//**//*

        String string = hexStringToString("");
        //  String s = PublicGmResult.decryptData("nY6ydgV9NKnqQrG1xI6U5QqOcXNPThiStw5pf66Tf5TN4gzU63c67yDu2pRVNuH0lWZ1/6MRP5j4HW475XRnazaYGj5y5VUvtQ6mOOrRqlPtrfVYh9y3ux6KpHR8Wsh8X2x5HEkXjiwXgZdQwQ7DSIXAU5pDbd+BA0XNLHOzmfHmx467aAgPvBbo4DZer+tyS1lfzeda3uvqNG/AvSlCpTpQ7vqvlstOxciQmfunQpFsuthlAOCzdcb9GtE2/GHnezKCSvibE7L85UuPOEiNoJnlM9GFN5ixVCwIrtemHjhoiEaivjiq2If329fjk3OJyTPCJKY07EvfgZoxv2gvVEfKCi1exmIuqI3uz5b8lX/5mgSdL7mzCslrsTTpIJVY8XzX++pKwDiK4gKztFpnr8kvo9X2067xnSE2V1FembiJE7V8Nui3pYtVjf90vGgMYnj8TO/z/9c812LzGkNrTfzxoPcNoFLKmeOM32SUTuZjBHdxPCb+jcu7ll4xeso4di5k46On0r6lR0JGlbnwRfBU9VTQvehzKXIyjwXUlHJAx2NFeQfXDCjVNU484+FL5FRU8BaeH1Runuyt62MwITBN241YVUiW5SdViBMcK/MTwX7FocHgvkB294IfjKmUd2Xnf8Uy0by7JRtT6WnQen+CFE/PnmtMz2UVglbAPCL63R2ftP8dbTpyPNlCaKjIL4psx2VXHBTe0+Z1+ULoj/MnhQm/4NBGp9cJXEJP0zoCnZ8ImtcIoDw0RA4KtJyebRwawEJSgVlmba1nL4luvClUpamRVsAyHrEPLp+aLHkBCREiTKjIqbbnSVGzKfRSXySx/EBcF7wHvDs71cpmAw== lsz9ldsqlsz9ldsq wgrfwwcxwgrfwwcx");
        System.out.println(PublicGmResult.decryptData(string));*//*
*/

    }



}
