package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 顿顿
 * @date 20/9/14 14:18
 * @des 住院预缴信息
 */
public class HospitalRecordRootBean implements Serializable {
    private static final long serialVersionUID = 8264729636937224768L;

    private HospitalInfoBean item;
    private List<HospitalRecordBean> orderRecord;

    public HospitalInfoBean getItem() {
        return item;
    }

    public void setItem(HospitalInfoBean item) {
        this.item = item;
    }

    public List<HospitalRecordBean> getOrderRecord() {
        return orderRecord;
    }

    public void setOrderRecord(List<HospitalRecordBean> orderRecord) {
        this.orderRecord = orderRecord;
    }
}
