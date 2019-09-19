package com.keydom.ih_patient.bean.entity;

import java.io.Serializable;
import java.util.List;

public class GetLogisicBean implements Serializable {

    private int total;
    private int size;
    private int pages;
    private List<LogisticsEntity> records;




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


    public List<LogisticsEntity> getRecords() {
        return records;
    }

    public void setRecords(List<LogisticsEntity> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "GetLogisicBean{" +
                "total=" + total +
                ", size=" + size +
                ", pages=" + pages +
                ", records=" + records +
                '}';
    }


}
