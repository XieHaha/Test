package com.keydom.ih_patient.bean.entity.pharmacy;

import java.io.Serializable;

public class PrescriptionItemEntity implements Serializable {

    /**
     * 主键id
     */
    private int id;
    /**
     * 处方类型 0-普通 1-儿童
     */
    private String prescriptionItemType;
    /**
     * 处方明细序号
     */
    private String prescriptionItemNumber;
    /**
     * 处方单号
     */
    private String prescriptionId;
    /**
     * 药品代码
     */
    private String drugsCode;
    /**
     * 药品名称
     */
    private String drugsName;
    /**
     * 药品规格
     */
    private String spec;
    /**
     * 剂型
     */
    private String preparation;
    /**
     * 剂量
     */
    private String dosage;
    /**
     * 生产厂家
     */
    private String manufacturerName;
    /**
     * 单位
     */
    private String unit;
    /**
     * 用法
     */
    private String instruction;
    /**
     * 频率
     */
    private String frequency;
    /**
     * 天数
     */
    private String days;
    /**
     * 总剂量
     */
    private String sumDosage;
    /**
     * 单价
     */
    private String price;


    /**
     * 计价总量（数量）
     */
    private int quantity;
     /**
     * 包装单位
     */

    private String packUnit;
    /**
     * 金额
     */
    private String fee;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrescriptionItemType() {
        return prescriptionItemType;
    }

    public void setPrescriptionItemType(String prescriptionItemType) {
        this.prescriptionItemType = prescriptionItemType;
    }

    public String getPrescriptionItemNumber() {
        return prescriptionItemNumber;
    }

    public void setPrescriptionItemNumber(String prescriptionItemNumber) {
        this.prescriptionItemNumber = prescriptionItemNumber;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

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

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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


    public String getPackUnit() {
        return packUnit;
    }

    public void setPackUnit(String packUnit) {
        this.packUnit = packUnit;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "PrescriptionItemEntity{" +
                "id=" + id +
                ", prescriptionItemType='" + prescriptionItemType + '\'' +
                ", prescriptionItemNumber='" + prescriptionItemNumber + '\'' +
                ", prescriptionId='" + prescriptionId + '\'' +
                ", drugsCode='" + drugsCode + '\'' +
                ", drugsName='" + drugsName + '\'' +
                ", spec='" + spec + '\'' +
                ", preparation='" + preparation + '\'' +
                ", dosage='" + dosage + '\'' +
                ", manufacturerName='" + manufacturerName + '\'' +
                ", unit='" + unit + '\'' +
                ", instruction='" + instruction + '\'' +
                ", frequency='" + frequency + '\'' +
                ", days='" + days + '\'' +
                ", sumDosage='" + sumDosage + '\'' +
                ", price='" + price + '\'' +
                ", quantity=" + quantity +
                ", packUnit='" + packUnit + '\'' +
                ", fee='" + fee + '\'' +
                '}';
    }
}
