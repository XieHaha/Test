package com.keydom.ih_doctor.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：药品对象
 * @Author：song
 * @Date：19/1/17 下午4:55
 * 修改人：xusong
 * 修改时间：19/1/17 下午4:55
 */
public class DrugBean implements Serializable,MultiItemEntity {
    private static final long serialVersionUID = 1L;
    public final static int TYPE_BODY = 1;
    private long id;
    /**
     * 药品名称
     */
    private String drugsName;
    /**
     * 给药途径
     */
    private String way;
    /**
     * 药品用法
     */
    private String usage;
    /**
     * 给药频率
     */
    private String frequency;
    /**
     * 药品规格
     */
    private String spec;
    /**
     * 药品厂商
     */
    private String manufacturerName;
    /**
     * 药品价格
     */
    private BigDecimal price = BigDecimal.ZERO;
    /**
     * 包装规格
     */
    private Double dosage;
    /**
     * 计量单位
     */
    private String dosageUnit;
    /**
     * 处方－药品数量
     */
    private int quantity;

    /**
     * 处方－给药天数
     */
    private int days;


    /**
     * 最大用药天数
     */
    private int maximumMedicationDays;

    /**
     * 最大门诊用量
     */
        private float singleMaximum;


    /**
     * 药品编码
     */
    private long drugsId;

    /**
     * 包装单位
     */
    private String packUnit;

    /**
     * 处方－医嘱
     */
    private String doctorAdvice;


    private double singleDose;

    private boolean isSelecte=false;

    private float times;

    private float rate;

    public float getTimes() {
        return times;
    }

    public void setTimes(float times) {
        this.times = times;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public boolean isSelecte() {
        return isSelecte;
    }

    public void setSelecte(boolean selecte) {
        isSelecte = selecte;
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

    public Double getDosage() {
        return dosage;
    }

    public void setDosage(Double dosage) {
        this.dosage = dosage;
    }

    public String getDosageUnit() {
        return dosageUnit;
    }

    public void setDosageUnit(String dosageUnit) {
        this.dosageUnit = dosageUnit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getMaximumMedicationDays() {
        return maximumMedicationDays;
    }

    public void setMaximumMedicationDays(int maximumMedicationDays) {
        this.maximumMedicationDays = maximumMedicationDays;
    }

    public float getSingleMaximum() {
        return singleMaximum;
    }

    public void setSingleMaximum(float singleMaximum) {
        this.singleMaximum = singleMaximum;
    }

    public long getDrugsId() {
        return drugsId;
    }

    public void setDrugsId(long drugsId) {
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

    public double getSingleDose() {
        return singleDose;
    }

    public void setSingleDose(double singleDose) {
        this.singleDose = singleDose;
    }

    @Override
    public int getItemType() {
        return TYPE_BODY;
    }
}
