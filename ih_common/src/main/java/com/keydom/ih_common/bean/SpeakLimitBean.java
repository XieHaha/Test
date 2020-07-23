package com.keydom.ih_common.bean;

import java.io.Serializable;

/**
 * @date 20/7/23 10:40
 */
public class SpeakLimitBean implements Serializable {
    private static final long serialVersionUID = 7900286939237479851L;
    /**
     * 0：允许发言，1：禁止
     */
    private int isLimit;
    private String id;
    private String userOrderId;
    private String doctorId;
    private String doctorName;
    private String doctorCode;

    public int getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(int isLimit) {
        this.isLimit = isLimit;
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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }
}
