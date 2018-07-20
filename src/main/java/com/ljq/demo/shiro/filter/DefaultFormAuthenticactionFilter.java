package com.ljq.demo.shiro.filter;

import com.ljq.demo.shiro.common.log.Log;
import com.ljq.demo.shiro.constant.UserConstant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description: 默认登录验证拦截器
 * @Author: junqiang.lu
 * @Date: 2018/7/20
 */
public class DefaultFormAuthenticactionFilter extends FormAuthenticationFilter {

    // 表单验证码中的 name,在 shiro 的配置文件中设置
    private String verificationCodeName;

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        Log.debug("登录操作");

        // 校验验证码操作
        HttpServletRequest req = WebUtils.toHttp(request);
        /**
         * 判断是否为登录操作
         */
        if(isLoginUrl(request) && req.getMethod().equalsIgnoreCase(POST_METHOD)){
            HttpSession session = req.getSession();
            Object verificationObject = session.getAttribute(UserConstant.VERIFICATION_KEY);
            if(verificationObject == null || "".equals(verificationObject)){
                Log.debug("验证码失效,请重新刷新");
                return false;
            }else{
                String verification = req.getParameter(this.getVerificationCodeName());
                if(verification.equals(verificationObject)){
                    Log.debug("验证码错误");
                    return false;
                }
                // 验证成功,移除验证码
                session.removeAttribute(UserConstant.VERIFICATION_KEY);
            }
        }
        return super.onAccessDenied(request, response);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        Log.debug("登录成功" + usernamePasswordToken.getUsername());
        return super.onLoginSuccess(token, subject, request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        Log.debug("登录失败" + usernamePasswordToken.getUsername());
        return super.onLoginFailure(token, e, request, response);
    }

    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        // 登录失败,设置失败的消息
        Log.info(ae.getMessage());
        super.setFailureAttribute(request, ae);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        Log.debug("执行登录操作");
        return super.executeLogin(request, response);
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Log.debug("登录拦截器");
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated() && isLoginUrl(request)){
            // 如果已经登录,并且请求的是登录请求,则直接跳转之前的页面
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            String redirectUtl = savedRequest != null ? savedRequest.getRequestURI() : savedRequest.getRequestUrl();
            WebUtils.redirectToSavedRequest(request,response,redirectUtl);
            return false;
        }
        return super.preHandle(request, response);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Log.debug("拦截器-是否允许访问");
        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * 判断是否为登录操作
     * @param request 请求
     * @return
     */
    private boolean isLoginUrl(ServletRequest request){
        HttpServletRequest req = WebUtils.toHttp(request);
        if((req.getRequestURL().toString()).contains(UserConstant.LOGIN_URL)){
            return true;
        }
        return false;
    }


    public String getVerificationCodeName() {
        return verificationCodeName;
    }

    public void setVerificationCodeName(String verificationCodeName) {
        this.verificationCodeName = verificationCodeName;
    }
}
