package com.keydom.ih_common.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @date 20/6/3 14:31
 * @des 检验单 （自定义消息）
 */
public class InspectionApplyBean implements Serializable {
    private static final long serialVersionUID = -8566700728479963990L;
    private int type;
    private int payState;
    private String id;
    private String amount;
    private String age;
    private String complaint;
    private String conditionDesc;
    private String diagnosis;
    private String doctorAdvice;
    private String elcCardNumber;
    private String historyAllergy;
    private String historyIllness;
    private String epidemicDiseaseHistory;
    private String illnessDate;
    private String hospitalId;
    private String inquiryOrderId;
    private String insCheckOrderId;
    private String patientId;
    private String patientName;
    private String sex;
    private ArrayList<CheckOutApplyBean> cateS;

    public String getEpidemicDiseaseHistory() {
        return epidemicDiseaseHistory;
    }

    public void setEpidemicDiseaseHistory(String epidemicDiseaseHistory) {
        this.epidemicDiseaseHistory = epidemicDiseaseHistory;
    }

    public String getIllnessDate() {
        return illnessDate;
    }

    public void setIllnessDate(String illnessDate) {
        this.illnessDate = illnessDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPayState() {
        return payState;
    }

    public void setPayState(int payState) {
        this.payState = payState;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    public void setConditionDesc(String conditionDesc) {
        this.conditionDesc = conditionDesc;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDoctorAdvice() {
        return doctorAdvice;
    }

    public void setDoctorAdvice(String doctorAdvice) {
        this.doctorAdvice = doctorAdvice;
    }

    public String getElcCardNumber() {
        return elcCardNumber;
    }

    public void setElcCardNumber(String elcCardNumber) {
        this.elcCardNumber = elcCardNumber;
    }

    public String getHistoryAllergy() {
        return historyAllergy;
    }

    public void setHistoryAllergy(String historyAllergy) {
        this.historyAllergy = historyAllergy;
    }

    public String getHistoryIllness() {
        return historyIllness;
    }

    public void setHistoryIllness(String historyIllness) {
        this.historyIllness = historyIllness;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getInquiryOrderId() {
        return inquiryOrderId;
    }

    public void setInquiryOrderId(String inquiryOrderId) {
        this.inquiryOrderId = inquiryOrderId;
    }

    public String getInsCheckOrderId() {
        return insCheckOrderId;
    }

    public void setInsCheckOrderId(String insCheckOrderId) {
        this.insCheckOrderId = insCheckOrderId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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

    public ArrayList<CheckOutApplyBean> getCateS() {
        return cateS;
    }

    public void setCateS(ArrayList<CheckOutApplyBean> cateS) {
        this.cateS = cateS;
    }
}
