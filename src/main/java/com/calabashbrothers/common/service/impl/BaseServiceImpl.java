package com.calabashbrothers.common.service.impl;

import com.calabashbrothers.common.dao.BaseDao;
import com.calabashbrothers.common.entity.BaseEntity;
import com.calabashbrothers.common.service.BaseService;
import com.github.pagehelper.PageHelper;

import java.util.List;

public abstract class BaseServiceImpl<T extends BaseEntity,Dao extends BaseDao<T>> implements BaseService<T> {

    private Dao dao;

    public T selectById(Long id){
        return this.getDao().selectByPrimaryKey(id);
    }

    public List<T> findAll(T entity){
        PageHelper.startPage(entity.getPage(),entity.getRows());
        return this.getDao().selectAll();
    }

    public int deleteById(Long id) {
        return this.getDao().deleteByPrimaryKey(id);
    }

    public int save(T entity) {
        return this.getDao().insert(entity);
    }

    public int  update(T entity){
        return this.getDao().updateByPrimaryKey(entity);
    }

    public Dao getDao() {
        return dao;
    }
    public void setDao(Dao dao) {
        this.dao = dao;
    }

}
