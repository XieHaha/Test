package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

public class PregnancyOrderDetailItem implements Serializable {
    private static final long serialVersionUID = 7253470717951654782L;


    /**
     * projectId : 1
     * projectName : 评估胎儿体重、胎位检查、、复查血常规、尿常规、宫高、腹围、胎心、血压、体重
     * appointType : 1
     * prenatalDate : 2020-01-19
     * timeInterval : 11:30~12:00
     */

    private String projectId;
    private String projectName;
    private int appointType; //1：检验检查；2：产检门诊；12：检验检查和产检门诊
    private String prenatalDate;
    private String timeInterval;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getAppointType() {
        return appointType;
    }

    public void setAppointType(int appointType) {
        this.appointType = appointType;
    }

    public String getPrenatalDate() {
        return prenatalDate;
    }

    public void setPrenatalDate(String prenatalDate) {
        this.prenatalDate = prenatalDate;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }
}
