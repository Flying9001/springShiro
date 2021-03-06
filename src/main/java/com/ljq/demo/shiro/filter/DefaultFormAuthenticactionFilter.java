package com.ljq.demo.shiro.filter;

import com.ljq.demo.shiro.common.log.Log;
import com.ljq.demo.shiro.constant.UserConstant;
import com.ljq.demo.shiro.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 默认登录验证拦截器
 * @Author: junqiang.lu
 * @Date: 2018/7/20
 */
@Component("formAuthenFilter")
public class DefaultFormAuthenticactionFilter extends FormAuthenticationFilter {

    /**
     * 拦截器最先执行的方法
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Log.debug("Filter-start intercept");
        Subject subject = SecurityUtils.getSubject();
        HttpServletRequest req = WebUtils.toHttp(request);
        /**
         * 校验 重复登录 与 登出 操作
         */
        if(subject.isAuthenticated() && isLoginUrl(req)){
            Log.debug("重复登录,用户名: " + subject.getPrincipal());
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            String redirectUtl = savedRequest != null ? savedRequest.getRequestUrl() : super.getSuccessUrl();
            WebUtils.redirectToSavedRequest(request,response,redirectUtl);
            return false;
        }else if(subject.isAuthenticated() && isLogoutUrl(req)){
            Log.debug("用户退出,用户名: " + subject.getPrincipal());
            subject.logout();
            return true;
        }
        return super.preHandle(request, response);
    }

    /**
     * 是否允许访问
     * 在该层进行权限分配与校验
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Log.debug("Filter-isAccessAllowed");

        /**
         * 判断是否为 登录请求
         */
        HttpServletRequest req = WebUtils.toHttp(request);
        if(isLoginUrl(req) && req.getMethod().equalsIgnoreCase(POST_METHOD)){
            User user = new User();
            user.setAccount(req.getParameter("account"));
            user.setPasscode(req.getParameter("passcode"));
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(new UsernamePasswordToken(user.getAccount(),user.getPasscode()));
            } catch (AuthenticationException e) {
                Log.error("登录失败,原因: - " + e.getMessage());
                return false;
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * 当访问被拒绝时执行
     * 即当 isAccessAllowed 返回 false 时执行,返回 true 时不执行
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        Log.debug("Filter-onAccessDenied");

//        // 校验验证码操作
//        HttpServletRequest req = WebUtils.toHttp(request);
//        /**
//         * 判断是否为登录操作
//         */
//        if(isLoginUrl(request) && req.getMethod().equalsIgnoreCase(POST_METHOD)){
//            HttpSession session = req.getSession();
//            Object verificationObject = session.getAttribute(UserConstant.VERIFICATION_KEY);
//            if(verificationObject == null || "".equals(verificationObject)){
//                Log.debug("验证码失效,请重新刷新");
//                return false;
//            }else{
//                String verification = req.getParameter(this.getVerificationCodeName());
//                if(verification.equals(verificationObject)){
//                    Log.debug("验证码错误");
//                    return false;
//                }
//                // 验证成功,移除验证码
//                session.removeAttribute(UserConstant.VERIFICATION_KEY);
//            }
//        }
        return super.onAccessDenied(request, response);
    }

    /**
     * 判断是否为登录操作
     * @param request 请求
     * @return
     */
    private boolean isLoginUrl(HttpServletRequest request){
        if((request.getRequestURL().toString()).contains(UserConstant.LOGIN_URL)){
            return true;
        }
        return false;
    }

    /**
     * 是否为登出操作
     *
     * @param request
     * @return
     */
    private boolean isLogoutUrl(HttpServletRequest request){
        if((request.getRequestURL().toString()).contains(UserConstant.LOGOUT_URL)){
            return true;
        }
        return false;
    }

}
