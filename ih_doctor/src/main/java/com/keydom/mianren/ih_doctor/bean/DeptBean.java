package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;


public class DeptBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
