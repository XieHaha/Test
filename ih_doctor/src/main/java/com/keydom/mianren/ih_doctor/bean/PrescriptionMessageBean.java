package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class PrescriptionMessageBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String historyIllness;
    private String mainComplaint;
    private String diagnosis;
    private BigDecimal fee;
    private long id;

    public String getHistoryIllness() {
        return historyIllness;
    }

    public void setHistoryIllness(String historyIllness) {
        this.historyIllness = historyIllness;
    }

    public String getMainComplaint() {
        return mainComplaint;
    }

    public void setMainComplaint(String mainComplaint) {
        this.mainComplaint = mainComplaint;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
