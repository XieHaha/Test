package com.keydom.ih_patient.bean;

/**
 * created date: 2019/4/1 on 14:06
 * des:护理科室实体类
 */
public class ChooseNursingBean {
    private long id;
    private String name;
    private String code;

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
}
