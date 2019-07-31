package com.keydom.ih_patient.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * created date: 2018/12/19 on 17:39
 * des:护理详情实体
 */
public class NursingOrderDetailBean implements Serializable{

    public static final int STATE0 = 0;
    public static final int STATE1 = 1;
    public static final int STATE2 = 2;
    public static final int STATE3 = 3;
    public static final int STATE4 = 4;
    public static final int STATE5 = -4;
    public static final int STATE6 = 5;
    public static final int Evaluted = 6;
    /**
     * userName : 姓名
     * reservationNumber : 预约号
     * telephone : 电话号码
     * address : 服务地址
     * time : Tue Dec 18 09:23:03 CST 2018
     * serviceObject : 服务对象
     * hospital : 医院名称
     * reservationSet : 100.0
     * conditionDesciption : 需求描述
     * conditionImage : 病理依据，病理依据
     * orderDetailItems : [{"serviceName":"打针","price":1}]
     */
    private long id;
    private NursingOrderDetailBean nursingServiceOrderDetailBaseDto;
    private String userName;
    private String reservationNumber;
    private String telephone;
    private String address;
    private String time;
    private String patientName;
    private String serviceObject;
    private String hospital;
    private BigDecimal reservationSet;
    private String conditionDesciption;
    private String conditionImage;
    private List<OrderDetailItemsBean> orderDetailItems;
    private List<NursingOrderServiceBean> orderDetailEachService;
    private List<NursingOrderServiceItem2Bean> subOrders;
    private List<NursingOrderService3Bean> equipmentItem;
    private int state;
    private String orderNumber;
    private List<String> serviceName;
    private String hospitalAddress;
    private String patientAge;
    private String patientSex;
    private String patientTel;
    private String longitude; //医院经度
    private String latitude; //医院纬度
    private String serviceAddress;
    private String city;
    private String serviceTotal;
    private String equipmentTotal;
    private String applyPhone;
    private String deptName;
    private String visitTime;
    private int frequency;
    private String visitPeriod;
    private long deptId;
    private long deptAreaId;

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getVisitPeriod() {
        return visitPeriod;
    }

    public void setVisitPeriod(String visitPeriod) {
        this.visitPeriod = visitPeriod;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public long getDeptAreaId() {
        return deptAreaId;
    }

    public void setDeptAreaId(long deptAreaId) {
        this.deptAreaId = deptAreaId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApplyPhone() {
        return applyPhone;
    }

    public void setApplyPhone(String applyPhone) {
        this.applyPhone = applyPhone;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getServiceTotal() {
        return serviceTotal;
    }

    public void setServiceTotal(String serviceTotal) {
        this.serviceTotal = serviceTotal;
    }

    public String getEquipmentTotal() {
        return equipmentTotal;
    }

    public void setEquipmentTotal(String equipmentTotal) {
        this.equipmentTotal = equipmentTotal;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public List<String> getServiceName() {
        return serviceName;
    }

    public void setServiceName(List<String> serviceName) {
        this.serviceName = serviceName;
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

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public List<NursingOrderServiceBean> getOrderDetailEachService() {
        return orderDetailEachService;
    }

    public void setOrderDetailEachService(List<NursingOrderServiceBean> orderDetailEachService) {
        this.orderDetailEachService = orderDetailEachService;
    }

    public List<NursingOrderServiceItem2Bean> getSubOrders() {
        return subOrders;
    }

    public void setSubOrders(List<NursingOrderServiceItem2Bean> subOrders) {
        this.subOrders = subOrders;
    }

    public List<NursingOrderService3Bean> getEquipmentItem() {
        return equipmentItem;
    }

    public void setEquipmentItem(List<NursingOrderService3Bean> equipmentItem) {
        this.equipmentItem = equipmentItem;
    }

    public NursingOrderDetailBean getNursingServiceOrderDetailBaseDto() {
        return nursingServiceOrderDetailBaseDto;
    }

    public void setNursingServiceOrderDetailBaseDto(NursingOrderDetailBean nursingServiceOrderDetailBaseDto) {
        this.nursingServiceOrderDetailBaseDto = nursingServiceOrderDetailBaseDto;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public BigDecimal getReservationSet() {
        return reservationSet;
    }

    public void setReservationSet(BigDecimal reservationSet) {
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

    public List<OrderDetailItemsBean> getOrderDetailItems() {
        return orderDetailItems;
    }

    public void setOrderDetailItems(List<OrderDetailItemsBean> orderDetailItems) {
        this.orderDetailItems = orderDetailItems;
    }

    public static class OrderDetailItemsBean implements Serializable{
        /**
         * serviceName : 打针
         * price : 1.0
         */

        private String serviceName;
        private double price;
        private BigDecimal totalPrice;
        private int frequency;

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
