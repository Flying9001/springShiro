package com.ljq.demo.shiro.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 用户
 * @Author: junqiang.lu
 * @Date: 2018/7/19
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -5900047813196665896L;

    private long id;

    private String account;

    private String passcode;

    private String nickName;

    private String createTime;

    private String lastLoginTime;

    private int status;

    private List<Role> roleList;
}
