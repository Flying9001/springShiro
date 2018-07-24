package com.ljq.demo.shiro.security;

import com.ljq.demo.shiro.common.api.ResponseCode;
import com.ljq.demo.shiro.common.cache.RedisCacheManager;
import com.ljq.demo.shiro.common.log.Log;
import com.ljq.demo.shiro.constant.UserConstant;
import com.ljq.demo.shiro.dao.UserDao;
import com.ljq.demo.shiro.entity.Permission;
import com.ljq.demo.shiro.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: shiro 安全认证与授权
 * @Author: junqiang.lu
 * @Date: 2018/7/20
 */
@Component("defaultRealm")
public class DefaultRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;
    @Autowired
    RedisCacheManager redisCacheManager;

    private static final String AUTHORIZATION_CACHE_NAME = "authorization";

    public DefaultRealm() {
        super.setAuthorizationCacheName(AUTHORIZATION_CACHE_NAME);
    }

    /**
     * 判断当前 subject 中的用户是否具有权限/角色
     * 当调用 subject,hasRole() 时执行
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Log.debug("doGetAuthorizationInfo(PrincipalCollection)");

        // 登录用户的用户名
        String account = (String) super.getAvailablePrincipal(principalCollection);
        User user = new User();
        user.setAccount(account);
        user = userDao.login(user);
        Log.debug("当前授权的用户: " + user.toString());

        // 设置角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        /**
         * 获取角色
         * 需要从 redis/数据库查询获取
         */
        for (int i = 0; i < user.getRoleList().size(); i++) {
            authorizationInfo.addRole(user.getRoleList().get(i).getName());
            /**
             * 添加权限
             * 需要从redis/数据库查询
             */
            for (Permission permission : user.getRoleList().get(i).getPermissionList()) {
                authorizationInfo.addStringPermission(permission.getApiUrl());
            }
        }
        return authorizationInfo;
    }

    /**
     * 判断当前 subject 中的用户是否存在
     * 当调用 subject.login() 时执行
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        Log.info("doGetAuthenticationInfo(AuthenticationToken)");

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = new User();
        user.setAccount(token.getUsername());
        user = userDao.findByAccount(user);
        if(user == null){
            Log.info(ResponseCode.ACCOUNT_NOT_EXIST.getMsg());
            throw new UnknownAccountException(ResponseCode.ACCOUNT_NOT_EXIST.getMsg());
        }else if(user.getStatus() == UserConstant.ACCOUNT_LOCKED){
            Log.info(ResponseCode.ACCOUNT_LOCK.getMsg());
            throw new LockedAccountException(ResponseCode.ACCOUNT_LOCK.getMsg());
        }

        /**
         * 不建议将 user 放入这里,只需要放 account 这样 Cookie 必较小
         */
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getAccount(),user.getPasscode(),
                ByteSource.Util.bytes(ByteSource.Util.bytes(user.getAccount())),getName());

        // TODO setSession 将用户信息存入 session/redis


        return authenticationInfo;
    }

    /**
     * 设置 session
     * @param key 键
     * @param value 值
     */
    private void setSession(String key, Object value){

        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            Session session = subject.getSession();
            session.setAttribute(key,value);
        }
    }



}
