package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class PageBean implements Serializable {


    private static final long serialVersionUID = -6675238008855798308L;
    @JSONField(name ="total")
    private String total;
    @JSONField(name ="size")
    private int size;
    @JSONField(name ="current")
    private int current;
    @JSONField(name ="pages")
    private String pages;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }
}
