package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：人员信息
 * @Author：song
 * @Date：18/11/20 下午1:51
 * 修改人：xusong
 * 修改时间：18/11/20 下午1:51
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userCode = "";
    private String name;
    private String hospitalName;
    private String deptName;
    private String jobTitle;
    private String qrcode;
    private String avatar;
    private String msspId;
    private Long hospitalId = Long.valueOf(0);
    private String cityName;
    private Long deptId = Long.valueOf(0);
    private Long id = Long.valueOf(0);

    public String getMsspId() {
        return msspId;
    }

    public void setMsspId(String msspId) {
        this.msspId = msspId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
