package com.keydom.ih_doctor.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：耗材对象
 * @Author：song
 * @Date：19/1/28 上午10:18
 * 修改人：xusong
 * 修改时间：19/1/28 上午10:18
 */
public class MaterialBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String code;
    private String name;
    private String spec;
    private BigDecimal univalent;
    private int priority;
    private int state;
    private float percentConversion;
    private long hospitalId;
    private long collarUnitId;
    private long unitId;
    private String unitName;
    private int materialAmount = 1;

    public String getUnitName() {
        return unitName == null ? "" : unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public BigDecimal getUnivalent() {
        return univalent;
    }

    public void setUnivalent(BigDecimal univalent) {
        this.univalent = univalent;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getPercentConversion() {
        return percentConversion;
    }

    public void setPercentConversion(float percentConversion) {
        this.percentConversion = percentConversion;
    }

    public long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public long getCollarUnitId() {
        return collarUnitId;
    }

    public void setCollarUnitId(long collarUnitId) {
        this.collarUnitId = collarUnitId;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public int getMaterialAmount() {
        return materialAmount;
    }

    public void setMaterialAmount(int materialAmount) {
        this.materialAmount = materialAmount;
    }
}
