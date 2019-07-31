package com.keydom.ih_doctor.bean;

import java.io.Serializable;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：报告子项
 * @Author：song
 * @Date：19/1/17 下午4:55
 * 修改人：xusong
 * 修改时间：19/1/17 下午4:55
 */
public class ReportItemBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
