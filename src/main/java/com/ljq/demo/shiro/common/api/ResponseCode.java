package com.ljq.demo.shiro.common.api;

/**
 * @Description: 返回数据枚举
 * @Author: junqiang.lu
 * @Date: 2018/7/19
 */
public enum ResponseCode {

    SUCCESS(1000, "SUCCESS"),

    PARAMS_ERROR(1001,"参数错误"),
    ACCOUNT_NOT_EXIST(1002,"帐号不存在"),
    ACCOUNT_LOCK(1003,"帐号被锁定"),



    FAIL(-1, "FAIL");

    // 返回码
    private int code;

    // 返回信息
    private String msg;



    private ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    // setter getter
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
