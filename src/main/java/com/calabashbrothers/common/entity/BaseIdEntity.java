package com.calabashbrothers.common.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 *  id为String的实现类
 *  约定所有entity的Id
 *  可以通过约定的 Id 来写公共方法
 */
public abstract class BaseIdEntity implements BaseId<Long>{

    public static final long serialVersionUID = 1L;

    @Id
    @Column
    public Long id;

    @Transient // 表明表中没有该字段
    private Integer page = 1;

    @Transient
    private Integer rows = 5;


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
