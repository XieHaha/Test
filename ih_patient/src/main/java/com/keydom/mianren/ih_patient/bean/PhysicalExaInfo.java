package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;

/**
 * 体检订单
 */
public class PhysicalExaInfo {

    @JSONField(name = "id")
    private long id;
    @JSONField(name = "hospitalCode")
    private String hospitalCode;
    @JSONField(name = "priority")
    private String priority;
    @JSONField(name = "code")
    private String code;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "icon")
    private String icon;
    @JSONField(name = "summary")
    private String summary;
    @JSONField(name = "checkTime")
    private String checkTime;
    @JSONField(name = "checkTimeDesc")
    private String checkTimeDesc;
    @JSONField(name = "address")
    private String address;
    @JSONField(name = "fee")
    private BigDecimal fee;
    @JSONField(name = "checkDetail")
    private String checkDetail;
    @JSONField(name = "checkProcess")
    private String checkProcess;
    @JSONField(name = "state")
    private String state;
    @JSONField(name = "creatorCode")
    private String creatorCode;
    @JSONField(name = "createTime")
    private String createTime;
    @JSONField(name = "remark")
    private String remark;
    @JSONField(name = "sold")
    private int sold;
    @JSONField(name = "isTop")
    private int isTop;

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
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

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckTimeDesc() {
        return checkTimeDesc;
    }

    public void setCheckTimeDesc(String checkTimeDesc) {
        this.checkTimeDesc = checkTimeDesc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getCheckDetail() {
        return checkDetail;
    }

    public void setCheckDetail(String checkDetail) {
        this.checkDetail = checkDetail;
    }

    public String getCheckProcess() {
        return checkProcess;
    }

    public void setCheckProcess(String checkProcess) {
        this.checkProcess = checkProcess;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatorCode() {
        return creatorCode;
    }

    public void setCreatorCode(String creatorCode) {
        this.creatorCode = creatorCode;
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

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }
}
