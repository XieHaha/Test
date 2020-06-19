package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

/**
 * @date 20/6/19 10:15
 * @des 用药原因
 */
public class UseDrugReasonBean implements Serializable {
    private static final long serialVersionUID = 1765921778856422008L;

    private String name;
    private String code;

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
