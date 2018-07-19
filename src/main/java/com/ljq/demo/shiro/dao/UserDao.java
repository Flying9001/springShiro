package com.ljq.demo.shiro.dao;

import com.ljq.demo.shiro.entity.Permission;
import com.ljq.demo.shiro.entity.Role;
import com.ljq.demo.shiro.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 用户DAO
 * @Author: junqiang.lu
 * @Date: 2018/7/19
 */
@Repository
public interface UserDao {


    /**
     * 用户登录
     *
     * @param user 用户身份信息
     * @return 用户完整信息
     */
    User login(User user);

    /**
     * 根据帐号查询用户信息
     *
     * @param user 用户帐号信息
     * @return 用户完整信息
     */
    User findByAccount(User user);

    /**
     * 查询用户角色列表
     * @param user 用户身份信息
     * @return 角色列表
     */
    List<Role> rolesList(User user);

    /**
     * 查询用户权限列表
     *
     * @param user 用户身份信息
     * @return
     */
    List<Permission> permissionList(User user);

    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return 添加结果
     */
    boolean addUser(User user);

    /**
     * 修改用户基本信息
     *
     * @param user 用户基本信息
     * @return 基本信息修改结果
     */
    boolean updateUserInfo(User user);

    /**
     * 修改用户角色
     *
     * @param user 用户信息
     * @param roleList 角色列表
     * @return 角色修改结果
     */
    int updateUserRoles(User user,List<Role> roleList);

    /**
     * 修改用户权限
     *
     * @param user 用户信息
     * @param permissionList 权限列表
     * @return 用户权限列表
     */
    int updateUserPermission(User user, List<Permission> permissionList);





}
