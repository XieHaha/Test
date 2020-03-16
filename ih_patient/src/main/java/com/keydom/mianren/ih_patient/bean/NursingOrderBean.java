package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * created date: 2018/12/12 on 15:52
 * des:护理订单实体
 */
public class NursingOrderBean implements Serializable{

    /**
     * id : 0
     * serverName : 姓名
     * price : 100.0
     * frequency : 1
     * orderNumber : 5
     * appointmentTime : 2018-12-18 00:00:00
     * serverObject : null
     * state : 4   -1 0 1 2 3 4
     * stateString : 已完成
     * canRejectOrder : false
     * pay : 0
     */

    private long id;
    private String serverName;
    private BigDecimal price;
    private int frequency; //服务总次数
    private int serviceFrequency;//当前服务次数
    private String orderNumber;
    private String appointmentTime;
    private String serverObject;
    private int state;
    private String stateString;
    private boolean canRejectOrder;
    private int pay;
    private String remark;
    private int patientAge;
    private String pateintName;
    private String patientSex;
    private int equipmentOrSubOrderPay;

    private int refundState;


//    private double subOrderTotalMoney;
//    private double equipmentOrderTotalMoney;
//
//    public double getSubOrderTotalMoney() {
//        return subOrderTotalMoney;
//    }
//
//    public void setSubOrderTotalMoney(double subOrderTotalMoney) {
//        this.subOrderTotalMoney = subOrderTotalMoney;
//    }
//
//    public double getEquipmentOrderTotalMoney() {
//        return equipmentOrderTotalMoney;
//    }
//
//    public void setEquipmentOrderTotalMoney(double equipmentOrderTotalMoney) {
//        this.equipmentOrderTotalMoney = equipmentOrderTotalMoney;
//    }


    public int getRefundState() {
        return refundState;
    }

    public void setRefundState(int refundState) {
        this.refundState = refundState;
    }

    public int getEquipmentOrSubOrderPay() {
        return equipmentOrSubOrderPay;
    }

    public void setEquipmentOrSubOrderPay(int equipmentOrSubOrderPay) {
        this.equipmentOrSubOrderPay = equipmentOrSubOrderPay;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public String getPateintName() {
        return pateintName;
    }

    public void setPateintName(String pateintName) {
        this.pateintName = pateintName;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getServerObject() {
        return serverObject;
    }

    public void setServerObject(String serverObject) {
        this.serverObject = serverObject;
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

    public boolean isCanRejectOrder() {
        return canRejectOrder;
    }

    public void setCanRejectOrder(boolean canRejectOrder) {
        this.canRejectOrder = canRejectOrder;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public int getServiceFrequency() {
        return serviceFrequency;
    }

    public void setServiceFrequency(int serviceFrequency) {
        this.serviceFrequency = serviceFrequency;
    }

    public boolean isFrequencyUsed(){
        return frequency == serviceFrequency;
    }
}
