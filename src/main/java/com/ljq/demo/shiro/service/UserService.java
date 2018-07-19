package com.ljq.demo.shiro.service;

import com.ljq.demo.shiro.common.api.ApiResult;

/**
 * @Description: 用户业务
 * @Author: junqiang.lu
 * @Date: 2018/7/19
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param params 登录参数(json格式)
     * @return
     */
    ApiResult login (String params);

    /**
     * 根据帐号查询用户信息
     * @param params 用户信息(json 格式)
     * @return
     */
    ApiResult findByAccount(String params);

    /**
     * 查询用户角色列表
     *
     * @param params 用户信息(json 格式)
     * @return 用户角色列表
     */
    ApiResult rolesList(String params);

    /**
     * 查询用户权限列表
     *
     * @param params 用户信息(json 格式)
     * @return 用户权限列表
     */
    ApiResult permissionList(String params);

    /**
     * 添加用户
     *
     * @param params 用户信息(json 格式)
     * @return 用户添加结果
     */
    ApiResult addUser(String params);

    /**
     * 修改用户基本信息
     *
     * @param params 用户信息(json 格式)
     * @return 用户基本信息修改结果
     */
    ApiResult updateUserInfo(String params);

    /**
     * 修改用户角色
     *
     * @param params 用户信息、角色列表(json 格式)
     * @return 用户角色修改结果
     */
    ApiResult updateUserRoles(String params);

    /**
     * 修改用户权限
     *
     * @param params 用户信息、权限列表(json 格式)
     * @return
     */
    ApiResult updateUserPermission(String params);



}
