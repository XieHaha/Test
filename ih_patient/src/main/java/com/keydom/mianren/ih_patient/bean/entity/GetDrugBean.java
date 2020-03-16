package com.keydom.mianren.ih_patient.bean.entity;

import java.io.Serializable;
import java.util.List;

public class GetDrugBean implements Serializable {

    private int total;
    private int size;
    private int pages;
    private List<GetDrugEntity> records;




    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<GetDrugEntity> getRecords() {
        return records;
    }

    public void setRecords(List<GetDrugEntity> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "GetDrugBean{" +
                "total=" + total +
                ", size=" + size +
                ", pages=" + pages +
                ", records=" + records +
                '}';
    }


}
