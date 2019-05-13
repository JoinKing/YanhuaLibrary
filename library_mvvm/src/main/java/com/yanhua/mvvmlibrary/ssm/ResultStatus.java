package com.yanhua.mvvmlibrary.ssm;

/**
 * @Author: gushao
 * @CreateDate: 2019/01/17
 * @Description: 响应value枚举
 * @Version: 1.0
 */
public enum ResultStatus {
    /**
     * 定义value以及描述
     */
    SUCCESS(100,"操作成功"),

    SUCCESS_ADD(101, "新增成功"),

    SUCCESS_DELETE(102,"删除成功"),

    SUCCESS_UPDATE(103,"修改成功"),

    SUCCESS_QUERY(104,"查询成功"),

    FAILED(200, "操作失败"),

    FAILED_ADD(201, "新增失败"),

    FAILED_DELETE(202, "删除失败"),

    FAILED_UPDATE(203, "修改失败"),

    FAILED_QUERY(204, "查询失败");


    private int value;

    private String reasonPhrase;

    ResultStatus (int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public String getReasonPhrase () {
        return reasonPhrase;
    }

    public static String getMessage (int value){
        for (ResultStatus obj : ResultStatus.values()) {
            if (obj.getValue() == value) {
                return obj.getReasonPhrase();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "{value:" + value + ", reasonPhrase:'" + reasonPhrase + "'}";
    }
}
