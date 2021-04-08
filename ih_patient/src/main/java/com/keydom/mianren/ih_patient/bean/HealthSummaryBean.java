package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @author 顿顿
 * @date 21/3/25 17:28
 * @des
 */
public class HealthSummaryBean implements Serializable {
    private static final long serialVersionUID = 8856975711703346032L;
    private String id;
    private String healthConclusionName;
    private String startTime;
    private String endTime;
    private String templateId;
    private String healthStatus;
    private int foodScore;
    private int exerciseScore;
    private int sleepScore;
    private int drinkScore;
    private int smokeSize;
    private String content;
    private String attention;
    private String doctorId;
    private String doctorName;
    private String patientId;
    private String patientName;
    private int isRead;
    private String isSaveTemplate;
    private String updateTime;
    private String createTime;
    private int isDel;
    private int bloodPressureCount;
    private int bloodSugarCount;
    private int bloodFatCount;
    private int heartRateCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHealthConclusionName() {
        return healthConclusionName;
    }

    public void setHealthConclusionName(String healthConclusionName) {
        this.healthConclusionName = healthConclusionName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public int getFoodScore() {
        return foodScore;
    }

    public void setFoodScore(int foodScore) {
        this.foodScore = foodScore;
    }

    public int getExerciseScore() {
        return exerciseScore;
    }

    public void setExerciseScore(int exerciseScore) {
        this.exerciseScore = exerciseScore;
    }

    public int getSleepScore() {
        return sleepScore;
    }

    public void setSleepScore(int sleepScore) {
        this.sleepScore = sleepScore;
    }

    public int getDrinkScore() {
        return drinkScore;
    }

    public void setDrinkScore(int drinkScore) {
        this.drinkScore = drinkScore;
    }

    public int getSmokeSize() {
        return smokeSize;
    }

    public void setSmokeSize(int smokeSize) {
        this.smokeSize = smokeSize;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
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

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getIsSaveTemplate() {
        return isSaveTemplate;
    }

    public void setIsSaveTemplate(String isSaveTemplate) {
        this.isSaveTemplate = isSaveTemplate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public int getBloodPressureCount() {
        return bloodPressureCount;
    }

    public void setBloodPressureCount(int bloodPressureCount) {
        this.bloodPressureCount = bloodPressureCount;
    }

    public int getBloodSugarCount() {
        return bloodSugarCount;
    }

    public void setBloodSugarCount(int bloodSugarCount) {
        this.bloodSugarCount = bloodSugarCount;
    }

    public int getBloodFatCount() {
        return bloodFatCount;
    }

    public void setBloodFatCount(int bloodFatCount) {
        this.bloodFatCount = bloodFatCount;
    }

    public int getHeartRateCount() {
        return heartRateCount;
    }

    public void setHeartRateCount(int heartRateCount) {
        this.heartRateCount = heartRateCount;
    }
}
