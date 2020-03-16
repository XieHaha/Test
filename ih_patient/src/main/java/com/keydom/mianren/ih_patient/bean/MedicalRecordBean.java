package com.keydom.mianren.ih_patient.bean;

/**
 * created date: 2019/1/4 on 15:44
 * des:病历记录
 */
public class MedicalRecordBean {

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
