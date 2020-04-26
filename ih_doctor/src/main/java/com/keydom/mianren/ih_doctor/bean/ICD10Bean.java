package com.keydom.mianren.ih_doctor.bean;

import android.text.TextUtils;

import java.io.Serializable;


public class ICD10Bean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String code;
    private String name;

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

    @Override
    public boolean equals(Object obj) {
        return TextUtils.equals(getCode(), ((ICD10Bean) obj).getCode());
    }
}
