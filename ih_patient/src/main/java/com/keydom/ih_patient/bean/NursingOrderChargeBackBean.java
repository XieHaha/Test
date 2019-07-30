package com.keydom.ih_patient.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * created date: 2018/12/21 on 17:45
 * des:护理退单实体
 */
public class NursingOrderChargeBackBean implements MultiItemEntity{
    private String projectName;
    private String orderNum;
    private int frequency;
    private String totalMoney;
    private String totalReturnMoney;
    private String fee;
    private String title;
    private List<NursingOrderChargeBackItem> canChargeBackMoney;
    private List<NursingOrderChargeBackItem> canNotChargeBackMoney;
    private String chargeRules;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTotalReturnMoney() {
        return totalReturnMoney;
    }

    public void setTotalReturnMoney(String totalReturnMoney) {
        this.totalReturnMoney = totalReturnMoney;
    }

    public String getChargeRules() {
        return chargeRules;
    }

    public void setChargeRules(String chargeRules) {
        this.chargeRules = chargeRules;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    private int chargeBackType;

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public int getChargeBackType() {
        return chargeBackType;
    }

    public void setChargeBackType(int chargeBackType) {
        this.chargeBackType = chargeBackType;
    }

    public List<NursingOrderChargeBackItem> getCanNotChargeBackMoney() {
        return canNotChargeBackMoney;
    }

    public List<NursingOrderChargeBackItem> getCanChargeBackMoney() {
        return canChargeBackMoney;
    }

    public void setCanChargeBackMoney(List<NursingOrderChargeBackItem> canChargeBackMoney) {
        this.canChargeBackMoney = canChargeBackMoney;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCanNotChargeBackMoney(List<NursingOrderChargeBackItem> canNotChargeBackMoney) {
        this.canNotChargeBackMoney = canNotChargeBackMoney;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    @Override
    public int getItemType() {
        return getChargeBackType();
    }
}
