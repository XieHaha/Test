package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：护理订单基本信息对象
 * @Author：song
 * @Date：18/12/21 下午3:20
 * 修改人：xusong
 * 修改时间：18/12/21 下午3:20
 */
public class HeadNurseServiceOrderDetailBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userName;
    private String reservationNumber;
    /**
     * 医生电话
     */
    private String telephone;
    /**
     * 申请人电话
     */
    private String applyPhone;
    private String serviceAddress;
    private String time;
    private String serviceObject;
    private String hospital;
    private String deptName;
    private String reservationSet;
    private String conditionDesciption;
    private String conditionImage;
    private int state;
    private String orderNumber;
    private String hospitalAddress;
    private String patientAge;
    private String patientSex;
    /**
     * 就诊人电话
     */
    private String patientTel;
    private String longitude;
    private String latitude;
    private int serviceFrequency;
    private int frequency;
    private List<String> serviceName;
    private List<OrderDetailItems> orderDetailItems;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getApplyPhone() {
        return applyPhone;
    }

    public void setApplyPhone(String applyPhone) {
        this.applyPhone = applyPhone;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getServiceObject() {
        return serviceObject;
    }

    public void setServiceObject(String serviceObject) {
        this.serviceObject = serviceObject;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getReservationSet() {
        return reservationSet;
    }

    public void setReservationSet(String reservationSet) {
        this.reservationSet = reservationSet;
    }

    public String getConditionDesciption() {
        return conditionDesciption;
    }

    public void setConditionDesciption(String conditionDesciption) {
        this.conditionDesciption = conditionDesciption;
    }

    public String getConditionImage() {
        return conditionImage;
    }

    public void setConditionImage(String conditionImage) {
        this.conditionImage = conditionImage;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public List<String> getServiceName() {
        return serviceName;
    }

    public void setServiceName(List<String> serviceName) {
        this.serviceName = serviceName;
    }

    public List<OrderDetailItems> getOrderDetailItems() {
        return orderDetailItems;
    }

    public void setOrderDetailItems(List<OrderDetailItems> orderDetailItems) {
        this.orderDetailItems = orderDetailItems;
    }

    class OrderDetailItems implements Serializable {
        private static final long serialVersionUID = 1L;
        private String serviceName;
        private int price;

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
