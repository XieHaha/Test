package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @author 顿顿
 * @date 21/4/7 13:33
 * @des
 */
public class SportsBean implements Serializable {
    private static final long serialVersionUID = -4775057383494497031L;

    private String createTime;
    private String exerciseId;
    private String imageUrl;
    private String patientId;
    private String recordTime;
    private String name;
    private String id;
    private int isDelete;
    private int minute;
    private int sumHeat;
    private int type;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSumHeat() {
        return sumHeat;
    }

    public void setSumHeat(int sumHeat) {
        this.sumHeat = sumHeat;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SportsBean itemBean = (SportsBean) o;

        return name.equals(itemBean.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
