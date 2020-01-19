package com.keydom.ih_patient.bean;

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
}
