package com.calabashbrothers.common.dao;


import com.calabashbrothers.common.entity.BaseEntity;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;


/**
 * 基础DAO
 */
public interface BaseDao<T extends BaseEntity> extends Mapper<T>, MySqlMapper<T> {

}
