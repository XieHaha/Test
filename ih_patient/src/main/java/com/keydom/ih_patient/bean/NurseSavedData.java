package com.keydom.ih_patient.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NurseSavedData implements Serializable {
    private String serviceTime;
    private String visitPeriod;
    private String serviceObjectName;
    private String serviceObjectCardNum;
    private String nurseDepartmentName;
    private String deptId;
    private String nurseDescripe;
    private List<NursingProjectInfo> projectList;
    public List<String> imgUrlList;
    private int mServiceNum;


    public String getVisitPeriod() {
        return visitPeriod;
    }

    public void setVisitPeriod(String visitPeriod) {
        this.visitPeriod = visitPeriod;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getServiceObjectName() {
        return serviceObjectName;
    }

    public void setServiceObjectName(String serviceObjectName) {
        this.serviceObjectName = serviceObjectName;
    }

    public String getServiceObjectCardNum() {
        return serviceObjectCardNum;
    }

    public void setServiceObjectCardNum(String serviceObjectCardNum) {
        this.serviceObjectCardNum = serviceObjectCardNum;
    }

    public String getNurseDepartmentName() {
        return nurseDepartmentName;
    }

    public void setNurseDepartmentName(String nurseDepartmentName) {
        this.nurseDepartmentName = nurseDepartmentName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getNurseDescripe() {
        return nurseDescripe;
    }

    public void setNurseDescripe(String nurseDescripe) {
        this.nurseDescripe = nurseDescripe;
    }

    public List<NursingProjectInfo> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<NursingProjectInfo> projectList) {
        this.projectList = projectList;
    }

    public List<String> getImgUrlList() {
        return imgUrlList;
    }

    public void setImgUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

    public int getmServiceNum() {
        return mServiceNum;
    }

    public void setmServiceNum(int mServiceNum) {
        this.mServiceNum = mServiceNum;
    }
}
