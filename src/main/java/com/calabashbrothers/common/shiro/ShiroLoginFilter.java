package com.calabashbrothers.common.shiro;

import com.calabashbrothers.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;


/**
 * 功能描述: <br>
 * 〈控制shiro的重定向请求〉
 *
 * @Author Yat-Xuan
 * @Description //TODO ASUS
 * @Date 2018/10/26 18:27
 **/
@RestController
@RequestMapping("/shiro")
@Slf4j
public class ShiroLoginFilter {

    /**
     * 功能描述: <br>
     * 〈在没有登录的情况下，返回json，不进行重定向。〉
     *
     * @return com.xuan.springappinfo.utils.Result
     * @Author Yat-Xuan
     * @Description //TODO ASUS
     * @Date 2018/10/26 18:29
     * @Param []
     **/
    @RequestMapping("/onAccessDenied")
    protected Result onAccessDenied() {
        log.info("拦截没有登录的请求!");
        return new Result(false, -1, "请登录！！！");
    }

    /**
     * 功能描述: <br>
     * 〈判断是否为ajax请求〉
     *
     * @return boolean
     * @Author Yat-Xuan
     * @Description //TODO ASUS
     * @Date 2018/10/26 17:38
     * @Param [request]
     **/
    private boolean isAjax(ServletRequest request) {
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        if ("XMLHttpRequest".equalsIgnoreCase(header)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
