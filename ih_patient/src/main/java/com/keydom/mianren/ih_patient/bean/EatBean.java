package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @author 顿顿
 * @date 21/4/2 15:01
 * @des
 */
public class EatBean implements Serializable {
    private static final long serialVersionUID = -2667099515575148079L;
    private int foodId;
    private float sumHeat;
    private int type;
    private float amount;
    private float copies;
    private String createTime;
    private String id;
    private String patientId;
    private String name;
    private String recordTime;

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public float getSumHeat() {
        return sumHeat;
    }

    public void setSumHeat(float sumHeat) {
        this.sumHeat = sumHeat;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getCopies() {
        return copies;
    }

    public void setCopies(float copies) {
        this.copies = copies;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EatBean itemBean = (EatBean) o;

        return name.equals(itemBean.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
