package com.keydom.mianren.ih_doctor.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class NursingProjectInfo implements Serializable {
    private static final long serialVersionUID = -3651653509140762541L;
    @JSONField(name = "id")
    private long id;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "icon")
    private String icon;
    @JSONField(name = "summary")
    private String summary;
    @JSONField(name = "intro")
    private String intro;
    @JSONField(name = "fee")
    private String fee;
    @JSONField(name = "hospitalId")
    private long hospitalId;
    @JSONField(name = "priority")
    private String priority;
    @JSONField(name = "cateId")
    private long cateId;
    @JSONField(name = "cate")
    private String cate;
    @JSONField(name = "state")
    private int state;
    @JSONField(name = "isTop")
    private int isTop;
    @JSONField(name = "creatorId")
    private long creatorId;
    @JSONField(name = "createTime")
    private String createTime;
    @JSONField(name = "remark")
    private String remark;
    @JSONField(name = "times")
    private String times;

    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public long getCateId() {
        return cateId;
    }

    public void setCateId(long cateId) {
        this.cateId = cateId;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }



}
