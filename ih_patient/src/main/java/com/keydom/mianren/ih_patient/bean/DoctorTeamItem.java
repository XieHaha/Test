package com.keydom.mianren.ih_patient.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.mianren.ih_patient.adapter.DoctorOrNurseDetailAdapter;

import java.io.Serializable;

/**
 * created date: 2019/1/3 on 15:58
 * des:医生团队
 */
public class DoctorTeamItem extends AbstractExpandableItem<DoctorTextItem> implements MultiItemEntity,Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String uuid;
    private String name;
    private String hospital;
    private String department;
    private String jobTitle;
    private String qrcode;
    private String avatar;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return DoctorOrNurseDetailAdapter.TYPE_TEAM;
    }
}
