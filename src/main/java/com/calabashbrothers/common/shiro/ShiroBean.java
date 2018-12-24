package com.calabashbrothers.common.shiro;

import com.calabashbrothers.common.shiro.filter.RolesOrFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/9/22
 * @Time: 18:40
 */
@Configuration
public class ShiroBean {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager, RolesOrFilter rolesOrFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // shiroFilterFactoryBean.setLoginUrl("/shiro/onAccessDenied");
        shiroFilterFactoryBean.setLoginUrl("/login.html");


        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();


        filterChainDefinitionMap.put("/statics/**", "anon");


        filterChainDefinitionMap.put("/login/getuser", "anon");
        filterChainDefinitionMap.put("/login/html", "anon");
        filterChainDefinitionMap.put("/403.html", "anon");


       // filterChainDefinitionMap.put("/backendUser/selBackendUserA", "rolesOr[超级管理员,人事]");
       // filterChainDefinitionMap.put("/getIndex", "rolesOr[admin,admin1]");

        // filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        Map<String, Filter> rolesOrFilterMap = new LinkedHashMap<>();
        rolesOrFilterMap.put("rolesOr", rolesOrFilter);

        shiroFilterFactoryBean.setFilters(rolesOrFilterMap);

        return shiroFilterFactoryBean;
    }

    @Bean(name = "rolesOrFilter")
    public RolesOrFilter getRolesOr() {
        return new RolesOrFilter();
    }

    @Bean(name = "customRealm")
    public CustomRealm customRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {

        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return customRealm;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(CustomRealm customRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm);

        return securityManager;
    }

    @Bean
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);

        return matcher;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor advisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public SessionManager sessionManager() {
        return new SessionManager();
    }
}
