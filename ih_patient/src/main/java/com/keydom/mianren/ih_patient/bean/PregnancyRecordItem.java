package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

public class PregnancyRecordItem implements Serializable {
    private static final long serialVersionUID = 8490392914507050951L;


    /**
     * recordId : 1215536710013231106
     * isFinsh : true
     * times : 第一次产检
     * prenatalDate : 2019-12-22
     * projectName : 建档、血常规、尿常规、宫高、腹围、胎心、血压、体重
     * preWeek : 怀孕12周
     * projectId : 1
     * isAppoint : 1
     */

    private String recordId;
    private boolean isFinsh;
    private String times;
    private String prenatalDate;
    private String projectName;
    private String preWeek;
    //检验检查
    private  String prenatalTimeInterval;
    //门诊时间
    private String appointDate;
    //门诊时段
    private String appointTimeInterval;
    //检验检查时段
    private String listInspectionRecord;
    private int projectId;
    private String isAppoint;//0 没有预约，1已预约


    public boolean isFinsh() {
        return isFinsh;
    }

    public void setFinsh(boolean finsh) {
        isFinsh = finsh;
    }
    
    public boolean isAppointed(){
        return "1".equals(isAppoint);
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getPrenatalDate() {
        return prenatalDate;
    }

    public void setPrenatalDate(String prenatalDate) {
        this.prenatalDate = prenatalDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPreWeek() {
        return preWeek;
    }

    public void setPreWeek(String preWeek) {
        this.preWeek = preWeek;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getIsAppoint() {
        return isAppoint;
    }

    public void setIsAppoint(String isAppoint) {
        this.isAppoint = isAppoint;
    }

    public String getListInspectionRecord() {
        return listInspectionRecord;
    }

    public void setListInspectionRecord(String listInspectionRecord) {
        this.listInspectionRecord = listInspectionRecord;
    }

    public String getPrenatalTimeInterval() {
        return prenatalTimeInterval;
    }

    public void setPrenatalTimeInterval(String prenatalTimeInterval) {
        this.prenatalTimeInterval = prenatalTimeInterval;
    }

    public String getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(String appointDate) {
        this.appointDate = appointDate;
    }

    public String getAppointTimeInterval() {
        return appointTimeInterval;
    }

    public void setAppointTimeInterval(String appointTimeInterval) {
        this.appointTimeInterval = appointTimeInterval;
    }
}
