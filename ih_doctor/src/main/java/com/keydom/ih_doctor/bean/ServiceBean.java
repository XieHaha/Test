package com.keydom.ih_doctor.bean;

import java.io.Serializable;


public class ServiceBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String name;
    private String code;
    private int state;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
