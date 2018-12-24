package com.calabashbrothers.com.leyou.service;

import java.util.List;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/12/24 0024
 * @Time: 19:29
 */
public interface LoginService {
    List<String> getPermissions(String userName);

    List<String> getRoles(String userName);

    String getUser(String userName);
}
