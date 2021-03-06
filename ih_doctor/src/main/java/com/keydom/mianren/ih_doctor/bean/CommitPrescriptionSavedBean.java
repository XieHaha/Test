package com.keydom.mianren.ih_doctor.bean;

import java.math.BigDecimal;
import java.util.List;

public class CommitPrescriptionSavedBean {
    private int isSaveTemplate;
    private String prescriptionTemplateName ;
    private String prescriptionTemplateType ;
    private List<DrugSavedBean> items;

    public int getIsSaveTemplate() {
        return isSaveTemplate;
    }

    public void setIsSaveTemplate(int isSaveTemplate) {
        this.isSaveTemplate = isSaveTemplate;
    }

    public String getPrescriptionTemplateName() {
        return prescriptionTemplateName;
    }

    public void setPrescriptionTemplateName(String prescriptionTemplateName) {
        this.prescriptionTemplateName = prescriptionTemplateName;
    }

    public String getPrescriptionTemplateType() {
        return prescriptionTemplateType;
    }

    public void setPrescriptionTemplateType(String prescriptionTemplateType) {
        this.prescriptionTemplateType = prescriptionTemplateType;
    }

    public List<DrugSavedBean> getItems() {
        return items;
    }

    public void setItems(List<DrugSavedBean> items) {
        this.items = items;
    }

    public static class DrugSavedBean{
        private String drugsName;
        private String frequency;
        private String frequencyEnglish;
        private String dosage;
        private String dosageUnit;
        private String spec;
        private String usage;
        private String singleDosage;
        private int days;
        /**
         * 处方－给药时长单位（天 小时、周）
         */
        private String daysUnit;
        private String way;
        private String wayCode;
        private String wayEnglish;
        private String doctorAdvice;
        private long id;
        private String packUnit;
        /**
         * 最大用药天数
         */
        private int maximumMedicationDays;
        /**
         * 最大门诊用量
         */
        private String singleMaximum;
        private float amount;

        /**
         * 药品价格
         */
        private BigDecimal price = BigDecimal.ZERO;
        private BigDecimal fee = BigDecimal.ZERO;


        /**
         * 处方－药品数量
         */
        private int quantity;

        /**
         * 药品代码
         */
        private String drugsCode;

        /**
         * 药品id
         */
        private Long drugsId;

        /**
         * 药品方
         */
        private String manufacturerName;
        private String tradeName;
        private String specificationImg;
        private String basicUnit;
        private String stock;
        private String preparation;
        private int state;
        private int seq;

        public String getPreparation() {
            return preparation;
        }

        public void setPreparation(String preparation) {
            this.preparation = preparation;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }

        public String getSingleDosage() {
            return singleDosage;
        }

        public void setSingleDosage(String singleDosage) {
            this.singleDosage = singleDosage;
        }

        public String getDrugsCode() {
            return drugsCode;
        }

        public void setDrugsCode(String drugsCode) {
            this.drugsCode = drugsCode;
        }

        public Long getDrugsId() {
            return drugsId;
        }

        public void setDrugsId(Long drugsId) {
            this.drugsId = drugsId;
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

        public String getPackUnit() {
            return packUnit;
        }

        public void setPackUnit(String packUnit) {
            this.packUnit = packUnit;
        }

        public String getDrugsName() {
            return drugsName;
        }

        public void setDrugsName(String drugsName) {
            this.drugsName = drugsName;
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

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getUsage() {
            return usage;
        }

        public void setUsage(String usage) {
            this.usage = usage;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public String getWay() {
            return way;
        }

        public String getDaysUnit() {
            return daysUnit;
        }

        public void setDaysUnit(String daysUnit) {
            this.daysUnit = daysUnit;
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

        public String getDoctorAdvice() {
            return doctorAdvice;
        }

        public void setDoctorAdvice(String doctorAdvice) {
            this.doctorAdvice = doctorAdvice;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
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

        public String getManufacturerName() {
            return manufacturerName;
        }

        public void setManufacturerName(String manufacturerName) {
            this.manufacturerName = manufacturerName;
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

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }
    }
}
