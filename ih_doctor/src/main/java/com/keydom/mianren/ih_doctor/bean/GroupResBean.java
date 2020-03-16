package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

public class GroupResBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String tid;
    private String groupName;
    private String avatar;
    private String owner;
    private boolean update;
    private List<DeptDoctorBean> hospitalUserVoList;
    private List<ImPatientInfo> registerUserVoList;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public List<DeptDoctorBean> getHospitalUserVoList() {
        return hospitalUserVoList;
    }

    public void setHospitalUserVoList(List<DeptDoctorBean> hospitalUserVoList) {
        this.hospitalUserVoList = hospitalUserVoList;
    }

    public List<ImPatientInfo> getRegisterUserVoList() {
        return registerUserVoList;
    }

    public void setRegisterUserVoList(List<ImPatientInfo> registerUserVoList) {
        this.registerUserVoList = registerUserVoList;
    }
}
