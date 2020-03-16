package com.keydom.mianren.ih_patient.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.mianren.ih_patient.adapter.PrescriptionListAdapter;

/**
 * created date: 2019/1/18 on 10:40
 * des:电子处方实体
 */
public class PrescriptionBean implements MultiItemEntity {
    private String name;
    private long id;
    private String num;
    private boolean isBottom;

    public boolean isBottom() {
        return isBottom;
    }

    public void setBottom(boolean bottom) {
        isBottom = bottom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public int getItemType() {
        return PrescriptionListAdapter.CONTENT;
    }
}
