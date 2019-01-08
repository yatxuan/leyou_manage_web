package com.calabashbrothers.common.controller;

import com.calabashbrothers.common.entity.BaseEntity;
import com.calabashbrothers.common.entity.Result;
import com.calabashbrothers.common.entity.StatusCode;
import com.calabashbrothers.common.service.BaseService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
public abstract class BaseController<T  extends BaseEntity,Service extends BaseService<T>> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public Service service;

    @RequestMapping("/create")
    public Result create(T model) {

        int count = getService().save(model);

        return new Result(true,count > 0 ? StatusCode.OK : StatusCode.ERROR, count > 0 ? "新增成功" : "新增失败");
    }

    @RequestMapping("/update")
    public Result update(T model) {

        int count = getService().update(model);

        return new Result(true,count > 0 ? StatusCode.OK : StatusCode.ERROR, count > 0 ? "修改成功" : "修改失败");
    }

    @RequestMapping("/show")
    public Result show(T model) {

        T object = getService().selectById(model.getId());

        return new Result(true,object != null ? StatusCode.OK : StatusCode.ERROR, object != null ? "查询成功" : "查询失败",object);
    }

    @RequestMapping("/showAll")
    public Result showAll(T model){

        List<T> list = getService().findAll(model);

        if (list != null && list.size() > 0){
            return new Result(true,StatusCode.OK,"查询成功",new PageInfo<T>(list));
        }
        return new Result(false,StatusCode.ERROR,"查询失败");
    }

    @RequestMapping("/destory")
    public Result destroy(T model) {

        int count = getService().deleteById(model.getId());

        return new Result(true,count > 0 ? StatusCode.OK : StatusCode.ERROR, count > 0 ? "删除成功" : "删除失败");
    }



    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

}
