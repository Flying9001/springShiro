package com.ljq.demo.shiro.controller;

import com.ljq.demo.shiro.common.log.Log;
import com.ljq.demo.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

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
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    protected String login(HttpServletRequest request){


        Log.debug("用户登录请求-POST");
        // POST 请求用户登录操作,此处无需写登录的代码
        // DefaultFormAuthenticationFilter 自动处理登录请求
        // 登录失败此处字需要写少量失败逻辑,也可以不写

        return "index";

    }

    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping(value = "logout",method = {RequestMethod.GET,RequestMethod.POST})
    protected String logout(HttpServletRequest request){
        Log.debug("退出登录");
        return "login";
    }

    /**
     * 角色校验
     *
     * @return
     */
    @RequestMapping(value = "checkRole",method = {RequestMethod.GET,RequestMethod.POST})
    protected String checkRole(HttpServletRequest request){
        Log.debug("角色校验");
        return "success";
    }

    @RequestMapping(value = "checkPermission", method = {RequestMethod.GET,RequestMethod.POST})
    public String checkPermission(HttpServletRequest request){
        Log.debug("权限校验");
        return "success";
    }






}
