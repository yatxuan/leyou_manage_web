package com.calabashbrothers.common.shiro.filter;/*
package com.xuan.springappinfo.utils.shiro.filter;

import com.google.gson.Gson;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

*/
/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/1 0001
 * @Time: 15:42
 *//*

public class MyAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (this.isLoginRequest(request, response)) {
            if (this.isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }

                return this.executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [" + this.getLoginUrl() + "]");
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            ServerResponse serverResponse = ServerResponse.createByErrorMessage("未登录，无法访问该地址");
            Gson gson = GsonFactory.getGson();
            String s = gson.toJson(serverResponse);
            out.println(s);
            out.flush();
            out.close();
            return false;
        }
    }

}
*/
