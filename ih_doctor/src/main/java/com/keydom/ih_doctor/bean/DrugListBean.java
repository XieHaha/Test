package com.keydom.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

public class DrugListBean implements Serializable {
    private int position;
    private List<DrugBean> drugList;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<DrugBean> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<DrugBean> drugList) {
        this.drugList = drugList;
    }
}
