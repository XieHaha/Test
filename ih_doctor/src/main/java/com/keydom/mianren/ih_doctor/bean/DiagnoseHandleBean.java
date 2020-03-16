package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;


public class DiagnoseHandleBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String suggest;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }
}
