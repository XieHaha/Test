package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 检验报告对象类
 */
public class InspectionRecordInfo {
    @JSONField(name = "date")
    private String date;
    @JSONField(name = "checkoutRecordVO")
    private List<CheckoutRecordVOBean> checkoutRecordVO;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<CheckoutRecordVOBean> getCheckoutRecordVO() {
        return checkoutRecordVO;
    }

    public void setCheckoutRecordVO(List<CheckoutRecordVOBean> checkoutRecordVO) {
        this.checkoutRecordVO = checkoutRecordVO;
    }

    public static class CheckoutRecordVOBean {
        /**
         * checkoutCode : 25
         * checkoutName : 心电
         * applyNumber : 4
         * checkoutProjectId : 1
         */

        @JSONField(name = "checkoutCode")
        private String checkoutCode;
        @JSONField(name = "checkoutName")
        private String checkoutName;
        @JSONField(name = "applyNumber")
        private String applyNumber;
        @JSONField(name = "checkoutProjectId")
        private long checkoutProjectId;

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

        public String getApplyNumber() {
            return applyNumber;
        }

        public void setApplyNumber(String applyNumber) {
            this.applyNumber = applyNumber;
        }

        public long getCheckoutProjectId() {
            return checkoutProjectId;
        }

        public void setCheckoutProjectId(long checkoutProjectId) {
            this.checkoutProjectId = checkoutProjectId;
        }
    }
}
