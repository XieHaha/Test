package com.keydom.ih_patient.bean;

import java.io.Serializable;

/**
 * 电子处方内容
 * @Author：song
 * @Date：18/11/29 下午2:16
 */
public class PrescriptionDrugBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;

    private String drugsName;

    private String usage;

    private String frequency;

    private String spec;

    private int quantity;

    private String dosageUnit;

    private String way;

    private String singleDose;

    private String dosage;

    private String packUnit;

    public String getSingleDose() {
        return singleDose;
    }

    public void setSingleDose(String singleDose) {
        this.singleDose = singleDose;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getPackUnit() {
        return packUnit;
    }

    public void setPackUnit(String packUnit) {
        this.packUnit = packUnit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDrugsName() {
        return drugsName;
    }

    public void setDrugsName(String drugsName) {
        this.drugsName = drugsName;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDosageUnit() {
        return dosageUnit;
    }

    public void setDosageUnit(String dosageUnit) {
        this.dosageUnit = dosageUnit;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }
}
