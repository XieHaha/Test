package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @date 20/9/17 13:30
 * @des
 */
public class PrescriptionRecordDrugBean implements Serializable {
    private static final long serialVersionUID = -3534945455628143362L;
    private String prescriptionNumber;
    private String drugCode;
    private String drugName;
    private String route;
    private String spec;
    private String preparation;
    private String usage;
    private String dosage;
    private String dosageUnit;
    private String frequency;
    private String days;
    private String singleMaximumDose;
    private String quantity;
    private String price;
    private String amount;
    private String dispensingUnit;

    public String getDispensingUnit() {
        return dispensingUnit;
    }

    public void setDispensingUnit(String dispensingUnit) {
        this.dispensingUnit = dispensingUnit;
    }

    public String getPrescriptionNumber() {
        return prescriptionNumber;
    }

    public void setPrescriptionNumber(String prescriptionNumber) {
        this.prescriptionNumber = prescriptionNumber;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDosageUnit() {
        return dosageUnit;
    }

    public void setDosageUnit(String dosageUnit) {
        this.dosageUnit = dosageUnit;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getSingleMaximumDose() {
        return singleMaximumDose;
    }

    public void setSingleMaximumDose(String singleMaximumDose) {
        this.singleMaximumDose = singleMaximumDose;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
