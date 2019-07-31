package com.keydom.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：医生处方详情对象
 * @Author：song
 * @Date：18/11/29 下午2:11
 * 修改人：xusong
 * 修改时间：18/11/29 下午2:11
 */
public class DoctorPrescriptionDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String inquiryId;
    private int type;
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
    private String fee;
    private int isReturnVisit;
    private String mainComplaint;
    private String auxiliaryInspect;
    private String handleOpinion;
    private String historyIllness;
    private String historyAllergy;
    private String hospitalName;
    private String commonSeal;
    private int cate;
    private List<List<DrugBean>> list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getIsReturnVisit() {
        return isReturnVisit;
    }

    public void setIsReturnVisit(int isReturnVisit) {
        this.isReturnVisit = isReturnVisit;
    }

    public String getMainComplaint() {
        return mainComplaint;
    }

    public void setMainComplaint(String mainComplaint) {
        this.mainComplaint = mainComplaint;
    }

    public String getAuxiliaryInspect() {
        return auxiliaryInspect;
    }

    public void setAuxiliaryInspect(String auxiliaryInspect) {
        this.auxiliaryInspect = auxiliaryInspect;
    }

    public String getHandleOpinion() {
        return handleOpinion;
    }

    public void setHandleOpinion(String handleOpinion) {
        this.handleOpinion = handleOpinion;
    }

    public String getHistoryIllness() {
        return historyIllness;
    }

    public void setHistoryIllness(String historyIllness) {
        this.historyIllness = historyIllness;
    }

    public String getHistoryAllergy() {
        return historyAllergy;
    }

    public void setHistoryAllergy(String historyAllergy) {
        this.historyAllergy = historyAllergy;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getCommonSeal() {
        return commonSeal;
    }

    public void setCommonSeal(String commonSeal) {
        this.commonSeal = commonSeal;
    }

    public List<List<DrugBean>> getList() {
        return list;
    }

    public void setList(List<List<DrugBean>> list) {
        this.list = list;
    }

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
    }






}
