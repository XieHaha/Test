package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @author 顿顿
 * @date 21/4/2 9:39
 * @des 健康值
 */
public class HealthDataBean implements Serializable {
    private static final long serialVersionUID = -4098446047908110038L;

    private String id;
    private String patientId;
    private String writeDate;
    /**
     * 血糖
     */
    private float bloodSugar;
    /**
     * 总胆固醇
     */
    private float cholesterol;
    /**
     * 舒张压
     */
    private int diastolicPressure;
    /**
     * 心率值
     */
    private int heartRateValue;
    private int height;
    /**
     * 高密度脂蛋白胆固醇
     */
    private float highDensityLipoproteinCholesterol;
    /**
     * 低密度脂蛋白胆固醇
     */
    private float lowDensityLipoproteinCholesterol;
    /**
     * 收缩压
     */
    private int systolicPressure;
    /**
     * 甘油三酯
     */
    private float triglycerides;
    private int weight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public float getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(float bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public float getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(float cholesterol) {
        this.cholesterol = cholesterol;
    }

    public float getHighDensityLipoproteinCholesterol() {
        return highDensityLipoproteinCholesterol;
    }

    public void setHighDensityLipoproteinCholesterol(float highDensityLipoproteinCholesterol) {
        this.highDensityLipoproteinCholesterol = highDensityLipoproteinCholesterol;
    }

    public float getLowDensityLipoproteinCholesterol() {
        return lowDensityLipoproteinCholesterol;
    }

    public void setLowDensityLipoproteinCholesterol(float lowDensityLipoproteinCholesterol) {
        this.lowDensityLipoproteinCholesterol = lowDensityLipoproteinCholesterol;
    }

    public float getTriglycerides() {
        return triglycerides;
    }

    public void setTriglycerides(float triglycerides) {
        this.triglycerides = triglycerides;
    }

    public int getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public int getHeartRateValue() {
        return heartRateValue;
    }

    public void setHeartRateValue(int heartRateValue) {
        this.heartRateValue = heartRateValue;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public int getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
