package com.calabashbrothers.common.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/**
 * 重写shiro的授权方法
 *
 * @author ASUS
 */
public class RolesOrFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(javax.servlet.ServletRequest servletRequest,
                                      javax.servlet.ServletResponse servletResponse, Object object)
            throws Exception {

        Subject subject = getSubject(servletRequest, servletResponse);

        String[] roles = (String[]) object;

        if (roles == null || roles.length == 0) {
            return true;
        }

        for (String role : roles) {
            if (subject.hasRole(role)) {
                return true;
            }
        }

        return false;
    }
}
