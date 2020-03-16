package com.keydom.mianren.ih_doctor.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class PrescriptionBottomBean implements MultiItemEntity {
    public final static int TYPE_BOTTOM = 2;
    private String fee;
    private int bottomPosition;
    @Override
    public int getItemType() {
        return TYPE_BOTTOM;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public int getBottomPosition() {
        return bottomPosition;
    }

    public void setBottomPosition(int bottomPosition) {
        this.bottomPosition = bottomPosition;
    }
}
