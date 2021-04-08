package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @author 顿顿
 * @date 21/4/8 15:51
 * @des
 */
public class HealthConsultantBean implements Serializable {
    private static final long serialVersionUID = 988276046044736373L;
    private long id;
    private int isConsult;
    private String name;
    private String deptName;
    private String jobTitleName;
    private String hospitalName;
    private String userCode;
    private String adept;
    private String intro;
    private String image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIsConsult() {
        return isConsult;
    }

    public void setIsConsult(int isConsult) {
        this.isConsult = isConsult;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getJobTitleName() {
        return jobTitleName;
    }

    public void setJobTitleName(String jobTitleName) {
        this.jobTitleName = jobTitleName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getAdept() {
        return adept;
    }

    public void setAdept(String adept) {
        this.adept = adept;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
