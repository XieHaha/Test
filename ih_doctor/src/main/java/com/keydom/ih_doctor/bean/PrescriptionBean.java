package com.keydom.ih_doctor.bean;

import java.io.Serializable;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：处方信息
 * @Author：song
 * @Date：18/11/21 上午11:00
 * 修改人：xusong
 * 修改时间：18/11/21 上午11:00
 */
public class PrescriptionBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;

    private int cate;

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

    private int state;

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return this.sex;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return this.age;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorName() {
        return this.doctorName;
    }

    public void setDoctorDept(String doctorDept) {
        this.doctorDept = doctorDept;
    }

    public String getDoctorDept() {
        return this.doctorDept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDept() {
        return this.dept;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDiagnosis() {
        return this.diagnosis;
    }

    public void setAuditer(String auditer) {
        this.auditer = auditer;
    }

    public String getAuditer() {
        return this.auditer;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getAuditOpinion() {
        return this.auditOpinion;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getDelivery() {
        return this.delivery;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }


}
