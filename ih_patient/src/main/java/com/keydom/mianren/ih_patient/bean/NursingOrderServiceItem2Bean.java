package com.keydom.mianren.ih_patient.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.mianren.ih_patient.adapter.NursingOrderServiceAdapter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * created date: 2018/12/20 on 15:08
 * des:护理服务头部
 */
public class NursingOrderServiceItem2Bean implements MultiItemEntity,Serializable{


    /**
     * subOrderNumber : 1
     * serverProject : 子订单
     * frequency : 1
     * fee : 10.0
     * pay : 1
     */

    private String subOrderNumber;
    private String serverProject;
    private int frequency;
    private BigDecimal fee;
    private int pay;

    @Override
    public int getItemType() {
        return NursingOrderServiceAdapter.TYPE_LEVEL_2;
    }

    public String getSubOrderNumber() {
        return subOrderNumber;
    }

    public void setSubOrderNumber(String subOrderNumber) {
        this.subOrderNumber = subOrderNumber;
    }

    public String getServerProject() {
        return serverProject;
    }

    public void setServerProject(String serverProject) {
        this.serverProject = serverProject;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }
}
