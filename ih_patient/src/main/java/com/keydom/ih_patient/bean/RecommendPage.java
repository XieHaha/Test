package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class RecommendPage extends PageBean {

    @JSONField(name ="records")
    private List<RecommendDocAndNurBean> records;

    public List<RecommendDocAndNurBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecommendDocAndNurBean> records) {
        this.records = records;
    }
}
