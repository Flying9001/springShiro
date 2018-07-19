package com.ljq.demo.shiro.controller;

import com.ljq.demo.shiro.common.api.ApiResult;
import com.ljq.demo.shiro.common.log.Log;
import com.ljq.demo.shiro.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 用户控制中心
 * @Author: junqiang.lu
 * @Date: 2018/7/19
 */
@Controller
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param params 用户信息
     * @return 登录结果
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    protected ApiResult login(@RequestBody String params){
        ApiResult apiResult = null;

        try {
            apiResult = userService.login(params);
        } catch (Exception e) {
            Log.debug("user login error",e);
            return apiResult.failure();
        }

        return apiResult;
    }

}
