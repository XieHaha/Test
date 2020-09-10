package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * created date: 2019/1/4 on 15:44
 * des:病历记录
 *
 * @author 顿顿
 */
public class MedicalRecordBean implements Serializable {

    private static final long serialVersionUID = -3407551256844641730L;
    /**
     * id : 17
     * medicalId : 3
     * inspectId : 33
     * checkoutId : 0
     * prescriptionId : 0
     * diagnosis : 高血压
     * consultTime : 2019-01-18
     * type : 0
     */

    private long id;
    private long medicalId;
    private long inspectId;
    private long checkoutId;
    private long prescriptionId;
    private String diagnosis;
    private String consultTime;
    private long type;
    private String name;
    private String sex;
    private String age;
    private String doctorName;
    private String doctorDept;
    private String mainComplaint;
    private String auxiliaryInspect;
    private String handleOpinion;
    private String historyIllness;
    private String historyAllergy;
    private String time;
    private String hospitalName;
    private String commonSeal;
    private String cardNo;
    private String patientName;
    private String doctorCode;
    private String inquiryTime;
    private String complaint;
    private String presentHistory;
    private String allergyHistory;
    private String inquiryDeptName;
    private String admissionDate;
    private String dandleOpinion;
    private String dischargeDate;
    private String medicalHistory;
    private String historyInfection;
    private String examinationHistory;
    private String familyHistory;
    private String dischargeSummary;
    private String hospitalDiagnosis;
    private String dischargeDiagnosis;
    private String dischargeState;
    private String treatment;
    private String historicalEpidemiology;
    private String personalHistory;
    private String bodyTemperature;
    private String heartRate;
    private String breathing;
    private String bloodPressure;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getInquiryTime() {
        return inquiryTime;
    }

    public void setInquiryTime(String inquiryTime) {
        this.inquiryTime = inquiryTime;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getPresentHistory() {
        return presentHistory;
    }

    public void setPresentHistory(String presentHistory) {
        this.presentHistory = presentHistory;
    }

    public String getAllergyHistory() {
        return allergyHistory;
    }

    public void setAllergyHistory(String allergyHistory) {
        this.allergyHistory = allergyHistory;
    }

    public String getInquiryDeptName() {
        return inquiryDeptName;
    }

    public void setInquiryDeptName(String inquiryDeptName) {
        this.inquiryDeptName = inquiryDeptName;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getDandleOpinion() {
        return dandleOpinion;
    }

    public void setDandleOpinion(String dandleOpinion) {
        this.dandleOpinion = dandleOpinion;
    }

    public String getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(String dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getHistoryInfection() {
        return historyInfection;
    }

    public void setHistoryInfection(String historyInfection) {
        this.historyInfection = historyInfection;
    }

    public String getExaminationHistory() {
        return examinationHistory;
    }

    public void setExaminationHistory(String examinationHistory) {
        this.examinationHistory = examinationHistory;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    public String getDischargeSummary() {
        return dischargeSummary;
    }

    public void setDischargeSummary(String dischargeSummary) {
        this.dischargeSummary = dischargeSummary;
    }

    public String getHospitalDiagnosis() {
        return hospitalDiagnosis;
    }

    public void setHospitalDiagnosis(String hospitalDiagnosis) {
        this.hospitalDiagnosis = hospitalDiagnosis;
    }

    public String getDischargeDiagnosis() {
        return dischargeDiagnosis;
    }

    public void setDischargeDiagnosis(String dischargeDiagnosis) {
        this.dischargeDiagnosis = dischargeDiagnosis;
    }

    public String getDischargeState() {
        return dischargeState;
    }

    public void setDischargeState(String dischargeState) {
        this.dischargeState = dischargeState;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getHistoricalEpidemiology() {
        return historicalEpidemiology;
    }

    public void setHistoricalEpidemiology(String historicalEpidemiology) {
        this.historicalEpidemiology = historicalEpidemiology;
    }

    public String getPersonalHistory() {
        return personalHistory;
    }

    public void setPersonalHistory(String personalHistory) {
        this.personalHistory = personalHistory;
    }

    public String getBodyTemperature() {
        return bodyTemperature;
    }

    public void setBodyTemperature(String bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getBreathing() {
        return breathing;
    }

    public void setBreathing(String breathing) {
        this.breathing = breathing;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMedicalId() {
        return medicalId;
    }

    public void setMedicalId(long medicalId) {
        this.medicalId = medicalId;
    }

    public long getInspectId() {
        return inspectId;
    }

    public void setInspectId(long inspectId) {
        this.inspectId = inspectId;
    }

    public long getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(long checkoutId) {
        this.checkoutId = checkoutId;
    }

    public long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getConsultTime() {
        return consultTime;
    }

    public void setConsultTime(String consultTime) {
        this.consultTime = consultTime;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }
}
