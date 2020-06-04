package com.keydom.ih_common.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：检查子项对象
 * @Author：song
 * @Date：19/1/14 下午2:02
 * 修改人：xusong
 * 修改时间：19/1/14 下午2:02
 */
public class CheckOutParentBean implements Serializable {
    private static final long serialVersionUID = 1586585847716670162L;
    private String advice;
    private String applicationCode;
    private String applicationName;
    private String executeDeptCode;
    private String executeDeptName;
    private String fee;
    @JSONField(name = "id")
    private String insCheckApplicationId;
    @JSONField(name = "cateCode")
    private String insCheckCateCode;
    @JSONField(name = "name")
    private String insCheckCateName;
    private String isDel;
    private ArrayList<CheckOutSubBean> items;

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getExecuteDeptCode() {
        return executeDeptCode;
    }

    public void setExecuteDeptCode(String executeDeptCode) {
        this.executeDeptCode = executeDeptCode;
    }

    public String getExecuteDeptName() {
        return executeDeptName;
    }

    public void setExecuteDeptName(String executeDeptName) {
        this.executeDeptName = executeDeptName;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public ArrayList<CheckOutSubBean> getItems() {
        return items;
    }

    public String getInsCheckApplicationId() {
        return insCheckApplicationId;
    }

    public void setInsCheckApplicationId(String insCheckApplicationId) {
        this.insCheckApplicationId = insCheckApplicationId;
    }

    public String getInsCheckCateCode() {
        return insCheckCateCode;
    }

    public void setInsCheckCateCode(String insCheckCateCode) {
        this.insCheckCateCode = insCheckCateCode;
    }

    public String getInsCheckCateName() {
        return insCheckCateName;
    }

    public void setInsCheckCateName(String insCheckCateName) {
        this.insCheckCateName = insCheckCateName;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public void setItems(ArrayList<CheckOutSubBean> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object obj) {
        return TextUtils.equals(getInsCheckCateCode(), ((CheckOutParentBean) obj).getInsCheckCateCode());
    }
}
