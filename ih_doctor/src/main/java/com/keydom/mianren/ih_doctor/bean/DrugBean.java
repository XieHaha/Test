package com.keydom.mianren.ih_doctor.bean;

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


/**
 * "manufacturerName": "四川制药",
 * "singleMaximum": "5",
 * "drugsName": "感冒药",
 * "spec": "1.2*50",
 * "packUnit": "袋",
 * "frequency": "一日1次",
 * "way": "口服1",
 * "usage": "内用1",
 * "dosageUnit": "袋",
 * "price": 0.01,
 * "id": 95,
 * "drugsCode": "21030020002",
 * "drugsId": 239,
 * "preparation": "中型",
 * "dosage": "21432"
 */
public class DrugBean implements Serializable, MultiItemEntity {
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
     * 给药途径code
     */
    private String wayCode;
    private String wayEnglish;
    /**
     * 药品用法
     */
    private String usage;
    /**
     * 给药频率
     */
    private String frequency;
    /**
     * 给药频率 英式
     */
    private String frequencyEnglish;
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
    private String dosage;
    /**
     * 计量单位
     */
    private String dosageUnit;
    /**
     * 处方－药品数量
     */
    private int quantity;

    /**
     * 处方－给药时长（天 小时、周）
     */
    private int days;
    /**
     * 处方－给药时长单位（天 小时、周）
     */
    private String daysUnit;

    private float amount;

    /**
     * 最大用药天数
     */
    private int maximumMedicationDays;

    /**
     * 最大门诊用量
     */
    private String singleMaximum;


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


    private boolean isSelecte = false;

    private float times;

    private float rate;

    /**
     * 药品代码
     */
    private String drugsCode;
    /**
     * 药品名称
     */

    /**
     * 剂型
     */
    private String preparation;

    /**
     * 总价格
     */
    private BigDecimal fee;

    private String singleDosage;
    private String tradeName;
    private String specificationImg;
    private String basicUnit;

    private String stock;

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getSingleDosage() {
        return singleDosage;
    }

    public void setSingleDosage(String singleDosage) {
        this.singleDosage = singleDosage;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getSpecificationImg() {
        return specificationImg;
    }

    public void setSpecificationImg(String specificationImg) {
        this.specificationImg = specificationImg;
    }

    public String getBasicUnit() {
        return basicUnit;
    }

    public void setBasicUnit(String basicUnit) {
        this.basicUnit = basicUnit;
    }

    public String getDrugsCode() {
        return drugsCode;
    }

    public void setDrugsCode(String drugsCode) {
        this.drugsCode = drugsCode;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }


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

    public String getWayCode() {
        return wayCode;
    }

    public void setWayCode(String wayCode) {
        this.wayCode = wayCode;
    }

    public String getWayEnglish() {
        return wayEnglish;
    }

    public void setWayEnglish(String wayEnglish) {
        this.wayEnglish = wayEnglish;
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

    public String getFrequencyEnglish() {
        return frequencyEnglish;
    }

    public void setFrequencyEnglish(String frequencyEnglish) {
        this.frequencyEnglish = frequencyEnglish;
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

    public String getDaysUnit() {
        return daysUnit;
    }

    public void setDaysUnit(String daysUnit) {
        this.daysUnit = daysUnit;
    }

    public int getMaximumMedicationDays() {
        return maximumMedicationDays;
    }

    public void setMaximumMedicationDays(int maximumMedicationDays) {
        this.maximumMedicationDays = maximumMedicationDays;
    }

    public String getSingleMaximum() {
        return singleMaximum;
    }

    public void setSingleMaximum(String singleMaximum) {
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public int getItemType() {
        return TYPE_BODY;
    }
}
