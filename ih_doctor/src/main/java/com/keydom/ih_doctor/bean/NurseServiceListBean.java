package com.keydom.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：护理服务订单服务列表
 * @Author：song
 * @Date：18/12/21 上午11:23
 * 修改人：xusong
 * 修改时间：18/12/21 上午11:23
 */
public class NurseServiceListBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> severName;
    private String visitTime;
    private String visitPeriod;
    private String orderNumber;
    private String deptName;
    private String address;
    private String systemTime;
    private int frequency;
    private int serviceFrequency;
    private long id;
    private int state;

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getServiceFrequency() {
        return serviceFrequency;
    }

    public void setServiceFrequency(int serviceFrequency) {
        this.serviceFrequency = serviceFrequency;
    }

    public List<String> getSeverName() {
        return severName;
    }

    public void setSeverName(List<String> severName) {
        this.severName = severName;
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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
