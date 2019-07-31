package com.keydom.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：问诊记录子项对象
 * @Author：song
 * @Date：19/3/5 下午4:02
 * 修改人：xusong
 * 修改时间：19/3/5 下午4:02
 */
public class DiagnoseRecoderItemBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private long medicalId;
    private long inspectId;
    private long checkoutId;
    private long prescriptionId;
    private List<ReportItemBean> inspectList;
    private List<ReportItemBean> checkoutList;
    private String diagnosis;
    private String consultTime;
    private long type;

    public List<ReportItemBean> getInspectList() {
        return inspectList;
    }

    public void setInspectList(List<ReportItemBean> inspectList) {
        this.inspectList = inspectList;
    }

    public List<ReportItemBean> getCheckoutList() {
        return checkoutList;
    }

    public void setCheckoutList(List<ReportItemBean> checkoutList) {
        this.checkoutList = checkoutList;
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
