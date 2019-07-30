package com.keydom.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：护理订单、耗材列表
 * @Author：song
 * @Date：18/12/26 下午2:59
 * 修改人：xusong
 * 修改时间：18/12/26 下午2:59
 */
public class NursingPatientEquipmentItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private int frequency;
    private int serviceFrequency;
    private int state;
    private String stateString;
    private double totalMoney = 0;
    private List<DetailEquipment> detailEquipment;

    public int getServiceFrequency() {
        return serviceFrequency;
    }

    public void setServiceFrequency(int serviceFrequency) {
        this.serviceFrequency = serviceFrequency;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
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

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public List<DetailEquipment> getDetailEquipment() {
        return detailEquipment;
    }

    public void setDetailEquipment(List<DetailEquipment> detailEquipment) {
        this.detailEquipment = detailEquipment;
    }
}
