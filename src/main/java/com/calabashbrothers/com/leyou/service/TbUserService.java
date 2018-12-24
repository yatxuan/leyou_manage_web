package com.calabashbrothers.com.leyou.service;

import java.util.List;

public interface TbUserService{

    List<String> getPermissions(String username);

    List<String> getRoles(String username);

    String getUser(String username);
}
