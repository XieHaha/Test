package com.keydom.ih_patient.bean.entity;

import java.io.Serializable;



public class PageableEntity implements Serializable {

    private int pageNum;
    private int pageSize;
    private String pageTimestamp;

    public String getPageTimestamp() {
        return pageTimestamp;
    }

    public void setPageTimestamp(String pageTimestamp) {
        this.pageTimestamp = pageTimestamp;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageableEntity{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", pageTimestamp='" + pageTimestamp + '\'' +
                '}';
    }
}

