package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 电子处方内容
 * @Author：song
 * @Date：18/11/29 下午2:16
 */
public class PrescriptionDrugBean implements Serializable {


    private static final long serialVersionUID = -6933620290452367925L;
    /**
     * 药品id
     */
    private Long id;
    /**
     * 药品名称
     */
    private String drugsName;

    /**
     * 给药途径
     */
    private String way;
    /**
     * 用法
     */
    private String usage;

    /**
     * 用药频率
     */
    private String frequency;

    /**
     * 规格
     */
    private String spec;

    /**
     * 厂商
     */
    private String manufacturerName;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 总价格
     */
    private BigDecimal fee;

    /**
     * 单次剂量
     */
    private String dosage;

    private String singleDosage;

    /**
     * 单次剂量单位
     */
    private String dosageUnit;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 用药天数
     */
    private Integer days;

    private Integer maximumMedicationDays;

    private String singleMaximum;

    private Long drugsId;

    private String packUnit;

    private String doctorAdvice;

    /**
     * 一天几次
     */
    private Double times;

    private Double rate;

    private Integer seq;

    /**
     * 处方id
     */
    private String prescriptionId;

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrugsName() {
        return drugsName;
    }

    public void setDrugsName(String drugsName) {
        this.drugsName = drugsName;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
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

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setSingleDosage(String singleDosage) {
        this.singleDosage = singleDosage;
    }

    public String getSingleDosage() {
        return singleDosage;
    }

    public String getDosageUnit() {
        return dosageUnit;
    }

    public void setDosageUnit(String dosageUnit) {
        this.dosageUnit = dosageUnit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getMaximumMedicationDays() {
        return maximumMedicationDays;
    }

    public void setMaximumMedicationDays(Integer maximumMedicationDays) {
        this.maximumMedicationDays = maximumMedicationDays;
    }

    public String getSingleMaximum() {
        return singleMaximum;
    }

    public void setSingleMaximum(String singleMaximum) {
        this.singleMaximum = singleMaximum;
    }

    public Long getDrugsId() {
        return drugsId;
    }

    public void setDrugsId(Long drugsId) {
        this.drugsId = drugsId;
    }

    public String getPackUnit() {
        return packUnit;
    }

    public void setPackUnit(String packUnit) {
        this.packUnit = packUnit;
    }

    public String getDoctorAdvice() {
        return doctorAdvice;
    }

    public void setDoctorAdvice(String doctorAdvice) {
        this.doctorAdvice = doctorAdvice;
    }

    public Double getTimes() {
        return times;
    }

    public void setTimes(Double times) {
        this.times = times;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
