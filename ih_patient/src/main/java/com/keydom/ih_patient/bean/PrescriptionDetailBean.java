package com.keydom.ih_patient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 电子处方详情
 * @Author：song
 * @Date：18/11/29 下午2:11
 */
public class PrescriptionDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String sex;

    private String age;

    private String doctorName;

    private String doctorDept;

    private String dept;

    private String diagnosis;

    private String auditer;

    private String auditOpinion;

    private String delivery;

    private String time;

    private String outpatientNumber;

    private String serialNumber;

    private String address;

    private String phoneNumber;
    private String hospitalName;

    private String fee;
    private int cate;
    private String checker;

    private List<PrescriptionDrugBean> list ;

    public int getCate() {
        return cate;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public void setCate(int cate) {
        this.cate = cate;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorDept() {
        return doctorDept;
    }

    public void setDoctorDept(String doctorDept) {
        this.doctorDept = doctorDept;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getAuditer() {
        return auditer;
    }

    public void setAuditer(String auditer) {
        this.auditer = auditer;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOutpatientNumber() {
        return outpatientNumber;
    }

    public void setOutpatientNumber(String outpatientNumber) {
        this.outpatientNumber = outpatientNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public List<PrescriptionDrugBean> getList() {
        return list;
    }

    public void setList(List<PrescriptionDrugBean> list) {
        this.list = list;
    }
}
