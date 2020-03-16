package com.keydom.mianren.ih_doctor.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class PrescriptionHeadBean  implements MultiItemEntity {

    public final static int TYPE_HEAD = 0;
    private String titleName;
    private int position;
    private int isOutPrescription;
    @Override
    public int getItemType() {
        return TYPE_HEAD;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getIsOutPrescription() {
        return isOutPrescription;
    }

    public void setIsOutPrescription(int isOutPrescription) {
        this.isOutPrescription = isOutPrescription;
    }
}
