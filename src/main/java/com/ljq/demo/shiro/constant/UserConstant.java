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

    private static final long serialVersionUID = -7756182076349802261L;
    /**
     * 帐号状态
     */
    public static final int ACCOUNT_OK = 1;  // 正常
    public static final int ACCOUNT_LOCKED = -1;  // 被锁定

    /**
     * 用户操作接口 URL
     */
    public static final String LOGIN_URL = "api/user/login";
    public static final String LOGOUT_URL = "api/user/logout";


}
