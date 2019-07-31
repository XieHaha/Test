package com.keydom.ih_doctor.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class InspectionDetailInof {

    /**
     * checkoutResultList : [{"id":1,"applyNumber":"2","name":"红细胞","code":"","result":"12.3","resultUnit":"10-9/L","referenceValue":"4-10"},{"id":2,"applyNumber":"2","name":"淋巴细胞","result":"16.38","resultUnit":"%","referenceValue":"20.0-40.0"}]
     * applyNumber : 2
     * partientNumber : 1233211234567
     * patientName : 朱红霞
     * sex : 0
     * age : 23
     * resultTime : 2018-12-14
     * reporter :
     * checkoutCode : 23
     * checkoutName : 血常规
     * checkoutProjectId : 1
     */

    @JSONField(name = "applyNumber")
    private String applyNumber;
    @JSONField(name = "partientNumber")
    private String partientNumber;
    @JSONField(name = "patientName")
    private String patientName;
    @JSONField(name = "sex")
    private String sex;
    @JSONField(name = "age")
    private String age;
    @JSONField(name = "resultTime")
    private String resultTime;
    @JSONField(name = "reporter")
    private String reporter;
    @JSONField(name = "checkoutCode")
    private String checkoutCode;
    @JSONField(name = "checkoutName")
    private String checkoutName;
    @JSONField(name = "checkoutProjectId")
    private long checkoutProjectId;
    @JSONField(name = "checkoutResultList")
    private List<CheckoutResultListBean> checkoutResultList;

    public String getApplyNumber() {
        return applyNumber;
    }

    public void setApplyNumber(String applyNumber) {
        this.applyNumber = applyNumber;
    }

    public String getPartientNumber() {
        return partientNumber;
    }

    public void setPartientNumber(String partientNumber) {
        this.partientNumber = partientNumber;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getResultTime() {
        return resultTime;
    }

    public void setResultTime(String resultTime) {
        this.resultTime = resultTime;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getCheckoutCode() {
        return checkoutCode;
    }

    public void setCheckoutCode(String checkoutCode) {
        this.checkoutCode = checkoutCode;
    }

    public String getCheckoutName() {
        return checkoutName;
    }

    public void setCheckoutName(String checkoutName) {
        this.checkoutName = checkoutName;
    }

    public long getCheckoutProjectId() {
        return checkoutProjectId;
    }

    public void setCheckoutProjectId(long checkoutProjectId) {
        this.checkoutProjectId = checkoutProjectId;
    }

    public List<CheckoutResultListBean> getCheckoutResultList() {
        return checkoutResultList;
    }

    public void setCheckoutResultList(List<CheckoutResultListBean> checkoutResultList) {
        this.checkoutResultList = checkoutResultList;
    }

    public static class CheckoutResultListBean {
        /**
         * id : 1
         * applyNumber : 2
         * name : 红细胞
         * code :
         * result : 12.3
         * resultUnit : 10-9/L
         * referenceValue : 4-10
         */

        @JSONField(name = "id")
        private long id;
        @JSONField(name = "applyNumber")
        private String applyNumber;
        @JSONField(name = "name")
        private String name;
        @JSONField(name = "code")
        private String code;
        @JSONField(name = "result")
        private String result;
        @JSONField(name = "resultUnit")
        private String resultUnit;
        @JSONField(name = "referenceValue")
        private String referenceValue;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getApplyNumber() {
            return applyNumber;
        }

        public void setApplyNumber(String applyNumber) {
            this.applyNumber = applyNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getResultUnit() {
            return resultUnit;
        }

        public void setResultUnit(String resultUnit) {
            this.resultUnit = resultUnit;
        }

        public String getReferenceValue() {
            return referenceValue;
        }

        public void setReferenceValue(String referenceValue) {
            this.referenceValue = referenceValue;
        }
    }
}
