package com.keydom.mianren.ih_doctor.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class PrescriptionBodyBean  implements MultiItemEntity {

    public final static int TYPE_BODY = 1;
    private int position;
    private int childPosition;
    private DrugBean drugBean;
    @Override
    public int getItemType() {
        return TYPE_BODY;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public DrugBean getDrugBean() {
        return drugBean;
    }

    public void setDrugBean(DrugBean drugBean) {
        this.drugBean = drugBean;
    }

    public int getChildPosition() {
        return childPosition;
    }

    public void setChildPosition(int childPosition) {
        this.childPosition = childPosition;
    }
}
