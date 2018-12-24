package com.calabashbrothers.common.shiro;

import com.calabashbrothers.com.leyou.service.LoginService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ASUS
 */
public class CustomRealm extends AuthorizingRealm {

    @Resource
    private LoginService loginService;

    /**
     * 做授权管理
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

//        1.从主体传过来的认证信息中获取用户名
        String userName = (String) principalCollection.getPrimaryPrincipal();

        Set<String> roles = getRolesByUserName(userName);

        Set<String> permissions = getPermissionsByUserName(userName);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }

    /**
     * 从数据库中获取权限功能数据
     *
     * @param userName 用户名
     * @return
     */
    private Set<String> getPermissionsByUserName(String userName) {

        List<String> list = loginService.getPermissions(userName);

        Set<String> set = new HashSet<>(list);

        return set;
    }

    /**
     * 从数据库中获取角色权限数据
     *
     * @param userName 用户名
     * @return
     */
    private Set<String> getRolesByUserName(String userName) {

        List<String> list = loginService.getRoles(userName);

        Set<String> set = new HashSet<>(list);

        return set;
    }

    /**
     * 做认证  管理
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

//        1.从主体传过来的认证信息中获取用户名
        String userName = (String) authenticationToken.getPrincipal();

//       2.通过用户名到数据库中获取凭证

        String passWord = getPassWordByUserName(userName);

        if (passWord == null) {
            return null;
        }

        SimpleAuthenticationInfo authenticationInfo =
                new SimpleAuthenticationInfo(userName, passWord, this.getClass().getName());

//        放入盐
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userName));

        return authenticationInfo;
    }

    /**
     * 访问数据库
     *
     * @param userName 姓名
     * @return
     */
    private String getPassWordByUserName(String userName) {

        return loginService.getUser(userName);
    }


    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("1", "admin2");
        System.out.println(md5Hash.toHex());
    }
}
