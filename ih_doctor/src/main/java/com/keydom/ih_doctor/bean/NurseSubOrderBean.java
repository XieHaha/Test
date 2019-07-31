package com.keydom.ih_doctor.bean;

import java.io.Serializable;

public class NurseSubOrderBean implements Serializable {
        private static final long serialVersionUID = 1L;
        private String subOrderNumber;
        private String serverProject;
        private int frequency;
        private float fee;
        //支付状态 0未支付 1支付，-1退单, 2退单中,3支付超时
        private int pay;
        private String nurseName;
        private String createTime;

        public String getNurseName() {
            return nurseName;
        }

        public void setNurseName(String nurseName) {
            this.nurseName = nurseName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public float getFee() {
            return fee;
        }

        public void setFee(float fee) {
            this.fee = fee;
        }

        public int getPay() {
            return pay;
        }

        public void setPay(int pay) {
            this.pay = pay;
        }
    }

