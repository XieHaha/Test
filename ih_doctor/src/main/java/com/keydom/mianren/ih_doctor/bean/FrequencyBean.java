package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

/**
 * @date 20/4/24 13:27
 * @des
 */
public class FrequencyBean implements Serializable {
    private static final long serialVersionUID = -962670400196900362L;

    private String id;
    private String priority;
    private String code;
    private String name;
    private String remark;
    private String times;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
