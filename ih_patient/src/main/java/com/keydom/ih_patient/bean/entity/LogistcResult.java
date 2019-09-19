package com.keydom.ih_patient.bean.entity;

import java.io.Serializable;
import java.util.List;

public class LogistcResult implements Serializable {

    List<LogisticsEntity>data;

    public List<LogisticsEntity> getData() {
        return data;
    }

    public void setData(List<LogisticsEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LogistcResult{" +
                "data=" + data +
                '}';
    }
}
