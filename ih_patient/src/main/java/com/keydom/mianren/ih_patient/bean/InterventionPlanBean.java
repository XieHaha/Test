package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @author 顿顿
 * @date 21/3/25 17:28
 * @des
 */
public class InterventionPlanBean implements Serializable {
    private static final long serialVersionUID = 8856975711703346032L;
    private int isRead;
    private String id;
    private String interventionPlanName;
    private String startTime;
    private String endTime;
    private String templateId;
    private String foodPlan;
    private String exercisePlan;
    private String drugsPlan;
    private String sleepPlan;
    private String patientId;
    private String patientName;
    private String doctorId;
    private String doctorName;
    private String updateTime;
    private String createTime;
    private String isDel;
    private String isSaveTemplate;
    private String userCode;

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterventionPlanName() {
        return interventionPlanName;
    }

    public void setInterventionPlanName(String interventionPlanName) {
        this.interventionPlanName = interventionPlanName;
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

    public String getFoodPlan() {
        return foodPlan;
    }

    public void setFoodPlan(String foodPlan) {
        this.foodPlan = foodPlan;
    }

    public String getExercisePlan() {
        return exercisePlan;
    }

    public void setExercisePlan(String exercisePlan) {
        this.exercisePlan = exercisePlan;
    }

    public String getDrugsPlan() {
        return drugsPlan;
    }

    public void setDrugsPlan(String drugsPlan) {
        this.drugsPlan = drugsPlan;
    }

    public String getSleepPlan() {
        return sleepPlan;
    }

    public void setSleepPlan(String sleepPlan) {
        this.sleepPlan = sleepPlan;
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

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getIsSaveTemplate() {
        return isSaveTemplate;
    }

    public void setIsSaveTemplate(String isSaveTemplate) {
        this.isSaveTemplate = isSaveTemplate;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
