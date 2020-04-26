package com.keydom.mianren.ih_doctor.bean;

import android.text.TextUtils;

import java.io.Serializable;


public class ICD10Bean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String code;
    private String name;
    private String id;
    private String userOrderId;
    private String prescriptionId;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(String userOrderId) {
        this.userOrderId = userOrderId;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    @Override
    public boolean equals(Object obj) {
        return TextUtils.equals(getCode(), ((ICD10Bean) obj).getCode());
    }
}
