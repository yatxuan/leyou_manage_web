package com.calabashbrothers.com.leyou.service.impl;


import com.calabashbrothers.com.leyou.bean.TbUser;
import com.calabashbrothers.com.leyou.mapper.TbUserMapper;
import com.calabashbrothers.com.leyou.service.TbUserService;
import com.calabashbrothers.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbUserServiceImpl extends BaseServiceImpl<TbUser, TbUserMapper> implements TbUserService {

    @Autowired
    @Override
    public void setDao(TbUserMapper mapper) {
        super.setDao(mapper);
    }

    @Override
    public List<String> getPermissions(String username) {
        return null;
    }

    @Override
    public List<String> getRoles(String username) {
        return null;
    }

    @Override
    public String getUser(String username) {
        return null;
    }
}
