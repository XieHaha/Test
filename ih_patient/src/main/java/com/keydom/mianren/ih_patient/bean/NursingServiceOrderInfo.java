package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 护理服务订单实体
 */
public class NursingServiceOrderInfo implements Serializable {
    private static final long serialVersionUID = -6136851825445488206L;

    /**
     * nursingServiceOrderDetailBaseDto : {"userName":"测试账号","reservationNumber":"747e6a71-93b6-464d-b22f-df1ccc9b8922","telephone":"","applyPhone":"15928108704","serviceAddress":"四川省成都市成华区成都东站","time":"2019-01-22 06:00-07:00","serviceObject":"兵","hospital":"人民医院","deptName":"科室二","reservationSet":50,"conditionDesciption":"","conditionImage":",","orderDetailItems":[{"serviceName":"基础","price":100,"frequency":1},{"serviceName":"输液","price":50,"frequency":1}],"state":-4,"orderNumber":"N2001920190109065813","serviceName":["输液"],"hospitalAddress":"天府软件园C11座","patientAge":22,"patientSex":"0","patientTel":"15528163526","longitude":"103.867086","latitude":"30.705216","city":"成都市","serviceFrequency":1,"frequency":1,"patientName":"兵"}
     */

    @JSONField(name = "nursingServiceOrderDetailBaseDto")
    private NursingServiceOrderDetailBaseDtoBean nursingServiceOrderDetailBaseDto;

    public NursingServiceOrderDetailBaseDtoBean getNursingServiceOrderDetailBaseDto() {
        return nursingServiceOrderDetailBaseDto;
    }

    public void setNursingServiceOrderDetailBaseDto(NursingServiceOrderDetailBaseDtoBean nursingServiceOrderDetailBaseDto) {
        this.nursingServiceOrderDetailBaseDto = nursingServiceOrderDetailBaseDto;
    }

    public static class NursingServiceOrderDetailBaseDtoBean implements Serializable{
        /**
         * userName : 测试账号
         * reservationNumber : 747e6a71-93b6-464d-b22f-df1ccc9b8922
         * telephone :
         * applyPhone : 15928108704
         * serviceAddress : 四川省成都市成华区成都东站
         * time : 2019-01-22 06:00-07:00
         * serviceObject : 兵
         * hospital : 人民医院
         * deptName : 科室二
         * reservationSet : 50
         * conditionDesciption :
         * conditionImage : ,
         * orderDetailItems : [{"serviceName":"基础","price":100,"frequency":1},{"serviceName":"输液","price":50,"frequency":1}]
         * state : -4
         * orderNumber : N2001920190109065813
         * serviceName : ["输液"]
         * hospitalAddress : 天府软件园C11座
         * patientAge : 22
         * patientSex : 0
         * patientTel : 15528163526
         * longitude : 103.867086
         * latitude : 30.705216
         * city : 成都市
         * serviceFrequency : 1
         * frequency : 1
         * patientName : 兵
         */

        @JSONField(name = "userName")
        private String userName;
        @JSONField(name = "reservationNumber")
        private String reservationNumber;
        @JSONField(name = "telephone")
        private String telephone;
        @JSONField(name = "applyPhone")
        private String applyPhone;
        @JSONField(name = "serviceAddress")
        private String serviceAddress;
        @JSONField(name = "time")
        private String time;
        @JSONField(name = "serviceObject")
        private String serviceObject;
        @JSONField(name = "hospital")
        private String hospital;
        @JSONField(name = "deptName")
        private String deptName;
        @JSONField(name = "reservationSet")
        private BigDecimal reservationSet;
        @JSONField(name = "conditionDesciption")
        private String conditionDesciption;
        @JSONField(name = "conditionImage")
        private String conditionImage;
        @JSONField(name = "state")
        private int state;
        @JSONField(name = "orderNumber")
        private String orderNumber;
        @JSONField(name = "hospitalAddress")
        private String hospitalAddress;
        @JSONField(name = "patientAge")
        private int patientAge;
        @JSONField(name = "patientSex")
        private String patientSex;
        @JSONField(name = "patientTel")
        private String patientTel;
        @JSONField(name = "longitude")
        private String longitude;
        @JSONField(name = "latitude")
        private String latitude;
        @JSONField(name = "city")
        private String city;
        @JSONField(name = "serviceFrequency")
        private int serviceFrequency;
        @JSONField(name = "frequency")
        private int frequency;
        @JSONField(name = "patientName")
        private String patientName;
        @JSONField(name = "orderDetailItems")
        private List<OrderDetailItemsBean> orderDetailItems;
        @JSONField(name = "serviceName")
        private List<String> serviceName;

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

        public int getPatientAge() {
            return patientAge;
        }

        public void setPatientAge(int patientAge) {
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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

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

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public List<OrderDetailItemsBean> getOrderDetailItems() {
            return orderDetailItems;
        }

        public void setOrderDetailItems(List<OrderDetailItemsBean> orderDetailItems) {
            this.orderDetailItems = orderDetailItems;
        }

        public List<String> getServiceName() {
            return serviceName;
        }

        public void setServiceName(List<String> serviceName) {
            this.serviceName = serviceName;
        }

        public static class OrderDetailItemsBean implements Serializable{
            /**
             * serviceName : 基础
             * price : 100
             * frequency : 1
             */

            @JSONField(name = "serviceName")
            private String serviceName;
            @JSONField(name = "price")
            private BigDecimal price;
            @JSONField(name = "frequency")
            private int frequency;

            @JSONField(name = "totalPrice")
            private BigDecimal totalPrice;

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
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

            public BigDecimal getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(BigDecimal totalPrice) {
                this.totalPrice = totalPrice;
            }
        }
    }
}
