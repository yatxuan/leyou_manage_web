package com.calabashbrothers.common.entity;

import java.io.Serializable;

/**
 *  id基类
 * @param <ID>
 */
public interface BaseId<ID extends Serializable> extends Serializable {

    public ID getId();

    public void setId(ID id);

}
