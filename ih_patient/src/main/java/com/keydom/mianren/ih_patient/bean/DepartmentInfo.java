package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 科室实体
 * @author 顿顿
 */
public class DepartmentInfo implements Serializable {
    private static final long serialVersionUID = -433857316931952030L;
    @JSONField(name = "id")
    private String id;
    @JSONField(name = "priority")
    private int priority;
    @JSONField(name = "deptCode")
    private String deptCode;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "abbreviation")
    private String abbreviation;
    @JSONField(name = "intro")
    private String intro;
    @JSONField(name = "alpha")
    private String alpha;
    @JSONField(name = "deptCateId")
    private long deptCateId;
    @JSONField(name = "remark")
    private String remark;
    @JSONField(name = "state")
    private int state;
    @JSONField(name = "hospitalAreaId")
    private long hospitalAreaId;
    @JSONField(name = "hospitalId")
    private long hospitalId;
    @JSONField(name = "hospitalAreaName")
    private String hospitalAreaName;
    @JSONField(name = "hospitalName")
    private String hospitalName;

    /**
     * 0 门诊科室 1住院科室 2全部
     */
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAlpha() {
        return alpha;
    }

    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    public long getDeptCateId() {
        return deptCateId;
    }

    public void setDeptCateId(long deptCateId) {
        this.deptCateId = deptCateId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getHospitalAreaId() {
        return hospitalAreaId;
    }

    public void setHospitalAreaId(long hospitalAreaId) {
        this.hospitalAreaId = hospitalAreaId;
    }

    public long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalAreaName() {
        return hospitalAreaName;
    }

    public void setHospitalAreaName(String hospitalAreaName) {
        this.hospitalAreaName = hospitalAreaName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}
