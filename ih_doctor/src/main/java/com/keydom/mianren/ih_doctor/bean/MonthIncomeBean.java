package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

/**
 * 描述：User
 *
 * @author : xsy
 * @data: 2018年11月16日 8点59分
 */
public class MonthIncomeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
