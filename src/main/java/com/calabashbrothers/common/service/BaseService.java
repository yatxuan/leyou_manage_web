package com.calabashbrothers.common.service;



import com.calabashbrothers.common.entity.BaseEntity;

import java.util.List;

/**
 * id必须为String 类型否则不能使用该方法
 */
public interface BaseService<T extends BaseEntity>{

    int deleteById(Long id);

    int save(T entity);

    T selectById(Long id);

    List<T> findAll(T entity);

    int update(T record);
}
