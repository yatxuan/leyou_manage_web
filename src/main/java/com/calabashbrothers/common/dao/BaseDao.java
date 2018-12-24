package com.calabashbrothers.common.dao;


import com.calabashbrothers.common.entity.BaseEntity;
import com.calabashbrothers.common.mapper.MyMapper;

import java.util.List;

/**
 * 基础DAO
 */
public interface BaseDao<T extends BaseEntity> extends MyMapper<T> {

    int deleteByPrimaryKeyO(Long id);

    int insertO(T entity);

    T selectByPrimaryKeyO(Long id);

    List<T> selectAll();

    int updateByPrimaryKeyO(T record);
}
