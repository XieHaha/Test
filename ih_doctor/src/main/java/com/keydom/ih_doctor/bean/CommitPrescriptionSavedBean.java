package com.keydom.ih_doctor.bean;

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
        private String dosage;
        private String dosageUnit;
        private String spec;
        private String usage;
        private int days;
        private String way;
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
        private float singleMaximum;

        /**
         * 药品价格
         */
        private BigDecimal price = BigDecimal.ZERO;

        /**
         * 处方－药品数量
         */
        private int quantity;

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

        public void setWay(String way) {
            this.way = way;
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
    }
}
