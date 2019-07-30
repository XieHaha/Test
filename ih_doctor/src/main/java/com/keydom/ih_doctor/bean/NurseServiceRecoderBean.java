package com.keydom.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：普通护士－护理订单－服务记录列表
 * @Author：song
 * @Date：18/12/26 下午2:50
 * 修改人：xusong
 * 修改时间：18/12/26 下午2:50
 */
public class NurseServiceRecoderBean implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int ALREADY_PAY = 1;
    private int frequency;
    private int serviceFrequency;
    private String acceptName;
    private String acceptTime;
    private String acceptDeptName;
    private String nurseName;
    private String serverNurseName;
    private String confirmTime;
    private String serviceBeginTime;
    private String serviceEndTime;
    private String nextAppointTime;
    private String nextAppointPeriod;
    private String equipment;
    private String remark;
    private String serviceProject;
    private int state;
    private List<SubOrder> subOrderDetails;

    public String getServiceProject() {
        return serviceProject;
    }

    public void setServiceProject(String serviceProject) {
        this.serviceProject = serviceProject;
    }

    public int getServiceFrequency() {
        return serviceFrequency;
    }

    public void setServiceFrequency(int serviceFrequency) {
        this.serviceFrequency = serviceFrequency;
    }

    public String getNextAppointPeriod() {
        return nextAppointPeriod;
    }

    public void setNextAppointPeriod(String nextAppointPeriod) {
        this.nextAppointPeriod = nextAppointPeriod;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getAcceptName() {
        return acceptName;
    }

    public void setAcceptName(String acceptName) {
        this.acceptName = acceptName;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getAcceptDeptName() {
        return acceptDeptName;
    }

    public void setAcceptDeptName(String acceptDeptName) {
        this.acceptDeptName = acceptDeptName;
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    public String getServerNurseName() {
        return serverNurseName;
    }

    public void setServerNurseName(String serverNurseName) {
        this.serverNurseName = serverNurseName;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getServiceBeginTime() {
        return serviceBeginTime;
    }

    public void setServiceBeginTime(String serviceBeginTime) {
        this.serviceBeginTime = serviceBeginTime;
    }

    public String getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(String serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    public String getNextAppointTime() {
        return nextAppointTime;
    }

    public void setNextAppointTime(String nextAppointTime) {
        this.nextAppointTime = nextAppointTime;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<SubOrder> getSubOrderDetails() {
        return subOrderDetails;
    }

    public void setSubOrderDetails(List<SubOrder> subOrderDetails) {
        this.subOrderDetails = subOrderDetails;
    }

    public class SubOrder implements Serializable {
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
}
