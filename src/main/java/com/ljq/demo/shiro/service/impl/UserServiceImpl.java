package com.ljq.demo.shiro.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljq.demo.shiro.common.api.ApiResult;
import com.ljq.demo.shiro.common.api.ResponseCode;
import com.ljq.demo.shiro.common.log.Log;
import com.ljq.demo.shiro.dao.UserDao;
import com.ljq.demo.shiro.entity.User;
import com.ljq.demo.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 用户业务具体实现
 * @Author: junqiang.lu
 * @Date: 2018/7/19
 */
@Service("userService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 用户登录
     *
     * @param params 登录参数(json格式)
     * @return
     */
    @Override
    public ApiResult login(String params) {

        User user = null;
        try {
        ObjectMapper objectMapper = new ObjectMapper();
            user = objectMapper.readValue(params,User.class);
            user = userDao.login(user);
            if(user == null || user.getId() <= 0){
                return new ApiResult(ResponseCode.ACCOUNT_NOT_EXIST);
            }

        } catch (IOException e) {
            Log.error("trans json to User object error",e);
            return new ApiResult(ResponseCode.PARAMS_ERROR);
        }

        // TODO 将用户权限放到 Redis 缓存中


        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("user",user);
        System.out.println("user: " + user.toString());

        return ApiResult.success(resultMap);
    }

    /**
     * 根据帐号查询用户信息
     * @param params 用户信息(json 格式)
     * @return
     */
    @Override
    public ApiResult findByAccount(String params) {
        return null;
    }

    /**
     * 查询用户角色列表
     *
     * @param params 用户信息(json 格式)
     * @return 用户角色列表
     */
    @Override
    public ApiResult rolesList(String params) {
        return null;
    }

    /**
     * 查询用户权限列表
     *
     * @param params 用户信息(json 格式)
     * @return 用户权限列表
     */
    @Override
    public ApiResult permissionList(String params) {
        return null;
    }

    /**
     * 添加用户
     *
     * @param params 用户信息(json 格式)
     * @return 用户添加结果
     */
    @Override
    public ApiResult addUser(String params) {
        return null;
    }

    /**
     * 修改用户基本信息
     *
     * @param params 用户信息(json 格式)
     * @return 用户基本信息修改结果
     */
    @Override
    public ApiResult updateUserInfo(String params) {
        return null;
    }

    /**
     * 修改用户角色
     *
     * @param params 用户信息、角色列表(json 格式)
     * @return 用户角色修改结果
     */
    @Override
    public ApiResult updateUserRoles(String params) {
        return null;
    }

    /**
     * 修改用户权限
     *
     * @param params 用户信息、权限列表(json 格式)
     * @return
     */
    @Override
    public ApiResult updateUserPermission(String params) {
        return null;
    }
}
