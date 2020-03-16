package com.keydom.mianren.ih_patient.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.mianren.ih_patient.adapter.NursingOrderServiceAdapter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * created date: 2018/12/20 on 15:08
 * des:护理器械
 */
public class NursingOrderService3Bean extends AbstractExpandableItem<NursingOrderServiceItem3Bean> implements MultiItemEntity,Serializable {

    private int serviceFrequency;//第几次
    private int state;
    private int pay;
    private String stateString;
    private BigDecimal totalMoney;
    private List<NursingOrderServiceItem3Bean> detailEquipment;

    public int getServiceFrequency() {
        return serviceFrequency;
    }

    public void setServiceFrequency(int serviceFrequency) {
        this.serviceFrequency = serviceFrequency;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateString() {
        return stateString;
    }

    public void setStateString(String stateString) {
        this.stateString = stateString;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public List<NursingOrderServiceItem3Bean> getDetailEquipment() {
        return detailEquipment;
    }

    public void setDetailEquipment(List<NursingOrderServiceItem3Bean> detailEquipment) {
        this.detailEquipment = detailEquipment;
    }


    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return NursingOrderServiceAdapter.TYPE_LEVEL_5;
    }
}
