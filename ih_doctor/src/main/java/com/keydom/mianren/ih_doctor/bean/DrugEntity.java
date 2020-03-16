package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

public class DrugEntity implements Serializable {
    private List<DrugBean>records;

    public List<DrugBean> getRecords() {
        return records;
    }

    public void setRecords(List<DrugBean> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "DrugEntity{" +
                "records=" + records +
                '}';
    }
}
