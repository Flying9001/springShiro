package com.ljq.demo.shiro.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 权限
 * @Author: junqiang.lu
 * @Date: 2018/7/19
 */
@Data
public class Permission implements Serializable {
    private static final long serialVersionUID = 7154137823881268359L;

    private long id;

    private String apiUrl;

    private String description;

}
