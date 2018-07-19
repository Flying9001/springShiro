package com.ljq.demo.shiro.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 角色
 * @Author: junqiang.lu
 * @Date: 2018/7/19
 */
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 8030267800352697167L;

    private long id;

    private String name;

    private String type;

    private List<Permission> permissionList;

}
