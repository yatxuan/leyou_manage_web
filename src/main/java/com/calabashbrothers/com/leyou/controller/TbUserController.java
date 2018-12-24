package com.calabashbrothers.com.leyou.controller;

import com.calabashbrothers.com.leyou.bean.TbUser;
import com.calabashbrothers.com.leyou.service.impl.TbUserServiceImpl;
import com.calabashbrothers.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("TbUser")
public class TbUserController extends BaseController<TbUser, TbUserServiceImpl> {

    @Override
    @Autowired
    public void setService(TbUserServiceImpl service){
        super.setService(service);
    }


}
