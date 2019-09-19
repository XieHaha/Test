package com.keydom.ih_patient.bean;

import java.io.Serializable;

public class DrugsDtos implements Serializable {

    private static final long serialVersionUID = 7758685218479202423L;
    /**
     * drugsCode : 00058D00006
     * drugsName : 藿香正气口服液
     * spec : 1
     * manufacturerName : 白云山制药总厂
     * preparation : 液体
     * dosage : 0.5
     * dosageUnit : 支
     * instruction : 口服
     * frequency : 一日三次
     * days : 5
     * sumDosage : 10
     * price : 12
     * quantity : 2
     * packUnit : 盒
     * fee : 0.0
     */

    private String drugsCode;
    private String drugsName;
    private String spec;
    private String manufacturerName;
    private String preparation;
    private String dosage;
    private String dosageUnit;
    private String instruction;
    private String frequency;
    private String days;
    private String sumDosage;
    private String price;
    private int quantity;
    private String packUnit;
    private double fee;

    public String getDrugsCode() {
        return drugsCode;
    }

    public void setDrugsCode(String drugsCode) {
        this.drugsCode = drugsCode;
    }

    public String getDrugsName() {
        return drugsName;
    }

    public void setDrugsName(String drugsName) {
        this.drugsName = drugsName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
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

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
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

    public String getSumDosage() {
        return sumDosage;
    }

    public void setSumDosage(String sumDosage) {
        this.sumDosage = sumDosage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPackUnit() {
        return packUnit;
    }

    public void setPackUnit(String packUnit) {
        this.packUnit = packUnit;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
