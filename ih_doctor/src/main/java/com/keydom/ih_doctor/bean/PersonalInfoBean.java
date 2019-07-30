package com.keydom.ih_doctor.bean;

import java.io.Serializable;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：个人基本信息
 * @Author：song
 * @Date：18/12/13 下午8:00
 * 修改人：xusong
 * 修改时间：18/12/13 下午8:00
 */
public class PersonalInfoBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String avatar;
    private String name;
    private String sex;
    private int autonymState;
    private String qualificationsCard;
    private String hospitalName;
    private String deptName;
    private String adept;
    private String intro;
    private long deptId;

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAutonymState() {
        return autonymState;
    }

    public void setAutonymState(int autonymState) {
        this.autonymState = autonymState;
    }

    public String getQualificationsCard() {
        return qualificationsCard;
    }

    public void setQualificationsCard(String qualificationsCard) {
        this.qualificationsCard = qualificationsCard;
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
}
