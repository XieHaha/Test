package com.keydom.ih_doctor.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class HospitalAreaInfo {

    @JSONField(name = "id")
    private long id;
    @JSONField(name = "priority")
    private String priority;
    @JSONField(name = "hospitalCode")
    private String hospitalCode;
    @JSONField(name = "hospitalName")
    private String hospitalName;
    @JSONField(name = "code")
    private String code;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "address")
    private String address;
    @JSONField(name = "phoneNumber")
    private String phoneNumber;
    @JSONField(name = "state")
    private int state;
    @JSONField(name = "level")
    private String level;
    @JSONField(name = "nature")
    private String nature;
    @JSONField(name = "remark")
    private String remark;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
