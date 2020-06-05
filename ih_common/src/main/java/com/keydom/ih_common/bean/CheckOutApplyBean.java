package com.keydom.ih_common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @date 20/6/5 9:25
 * @des
 */
public class CheckOutApplyBean implements Serializable {
    private static final long serialVersionUID = 1967292782326886994L;

    private String id;
    private String advice;
    private String applicationCode;
    private String applicationName;
    private String executeDeptCode;
    private String executeDeptName;
    private String fee;
    private String insCheckApplicationId;
    private String insCheckCateCode;
    private String insCheckCateName;
    private String isDel;
    private String insCheckApplicationCateId;
    private String insCheckItemCode;
    private String insCheckItemName;
    private String price;
    private List<CheckOutApplyBean> items;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getInsCheckApplicationCateId() {
        return insCheckApplicationCateId;
    }

    public void setInsCheckApplicationCateId(String insCheckApplicationCateId) {
        this.insCheckApplicationCateId = insCheckApplicationCateId;
    }

    public String getInsCheckItemCode() {
        return insCheckItemCode;
    }

    public void setInsCheckItemCode(String insCheckItemCode) {
        this.insCheckItemCode = insCheckItemCode;
    }

    public String getInsCheckItemName() {
        return insCheckItemName;
    }

    public void setInsCheckItemName(String insCheckItemName) {
        this.insCheckItemName = insCheckItemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<CheckOutApplyBean> getItems() {
        return items;
    }

    public void setItems(List<CheckOutApplyBean> items) {
        this.items = items;
    }
}
