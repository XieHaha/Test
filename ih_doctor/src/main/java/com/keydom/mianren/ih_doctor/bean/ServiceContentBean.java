package com.keydom.mianren.ih_doctor.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.mianren.ih_doctor.adapter.ServiceRecyclrViewAdapter;

import java.io.Serializable;

/**
 * created date: 2019/5/5 on 15:05
 */
public class ServiceContentBean implements Serializable,MultiItemEntity {
    public static int OPEN = 1;
    public static int CLOSE = 0;

    private long subId;
    private String subName;
    private String subCode;
    private int subState;

    public long getSubId() {
        return subId;
    }

    public void setSubId(long subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public int getSubState() {
        return subState;
    }

    public void setSubState(int subState) {
        this.subState = subState;
    }

    @Override
    public int getItemType() {
        return ServiceRecyclrViewAdapter.TYPE_CONTENT;
    }
}
