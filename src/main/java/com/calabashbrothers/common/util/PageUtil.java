package com.calabashbrothers.common.util;

/**
 * <p>Description: 分页工具类 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/13 0013
 * @Time: 16:54
 */
public class PageUtil {
    /**
     * 当前页码
     */
    private int currNo = 1;

    /**
     * 每页显示条数
     */
    private int size = 15;

    /**
     * 总页码
     */
    private int totalCurrNo;

    /**
     * 总数据
     */
    private int totalSize;

    public PageUtil() {
    }

    public PageUtil(int currNo, int size) {
        this.currNo = currNo == 0 ? 1 : currNo > this.totalCurrNo ? this.totalCurrNo : currNo;
        this.size = size == 0 ? 15 : size;
    }

    public int getCurrNo() {
        return currNo;
    }

    public void setCurrNo(int currNo) {
        this.currNo = currNo == 0 ? 1 : currNo > this.totalCurrNo ? this.totalCurrNo : currNo;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size == 0 ? 15 : size;
    }

    public int getTotalCurrNo() {
        return totalCurrNo;
    }

    public void setTotalCurrNo(int totalCurrNo) {
        this.totalCurrNo = totalCurrNo;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
        if (this.totalSize > 0) {
            this.totalCurrNo = this.totalSize % this.size == 0 ? this.totalSize / this.size : this.totalSize / this.size + 1;
        } else {
            this.totalCurrNo = 1;
        }
    }
}
