package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @author 顿顿
 * @date 21/3/29 15:50
 * @des 健康管理首页
 */
public class HealthManagerMainBean implements Serializable {
    private static final long serialVersionUID = -4312605928964697783L;
    private String patientId;
    private int isOpen;
    private int isPerfect;
    private int isOpenHeartHeadBloodVessel;
    private int isWriteHeartHeadBloodVessel;
    private int isOpenHighBloodPressure;
    private int isWriteHighBloodPressure;
    private int isOpenDiabetes;
    private int isWriteDiabetes;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public int getIsPerfect() {
        return isPerfect;
    }

    public void setIsPerfect(int isPerfect) {
        this.isPerfect = isPerfect;
    }

    public int getIsOpenHeartHeadBloodVessel() {
        return isOpenHeartHeadBloodVessel;
    }

    public void setIsOpenHeartHeadBloodVessel(int isOpenHeartHeadBloodVessel) {
        this.isOpenHeartHeadBloodVessel = isOpenHeartHeadBloodVessel;
    }

    public int getIsWriteHeartHeadBloodVessel() {
        return isWriteHeartHeadBloodVessel;
    }

    public void setIsWriteHeartHeadBloodVessel(int isWriteHeartHeadBloodVessel) {
        this.isWriteHeartHeadBloodVessel = isWriteHeartHeadBloodVessel;
    }

    public int getIsOpenHighBloodPressure() {
        return isOpenHighBloodPressure;
    }

    public void setIsOpenHighBloodPressure(int isOpenHighBloodPressure) {
        this.isOpenHighBloodPressure = isOpenHighBloodPressure;
    }

    public int getIsWriteHighBloodPressure() {
        return isWriteHighBloodPressure;
    }

    public void setIsWriteHighBloodPressure(int isWriteHighBloodPressure) {
        this.isWriteHighBloodPressure = isWriteHighBloodPressure;
    }

    public int getIsOpenDiabetes() {
        return isOpenDiabetes;
    }

    public void setIsOpenDiabetes(int isOpenDiabetes) {
        this.isOpenDiabetes = isOpenDiabetes;
    }

    public int getIsWriteDiabetes() {
        return isWriteDiabetes;
    }

    public void setIsWriteDiabetes(int isWriteDiabetes) {
        this.isWriteDiabetes = isWriteDiabetes;
    }
}
