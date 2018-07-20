package com.ljq.demo.shiro.constant;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 用户常量
 * @Author: junqiang.lu
 * @Date: 2018/7/20
 */
@Data
public class UserConstant implements Serializable {

    /**
     * 帐号状态
     */
    public static final int ACCOUNT_OK = 1;  // 正常
    public static final int ACCOUNT_LOCKED = -1;  // 被锁定

    /**
     * 用户操作 URL
     */
    public static final String LOGIN_URL = "api/user/login";

    /**
     * session 验证 key
     */
    public static final String VERIFICATION_KEY = "SESSION_VERIFICATION_KEY";



}
