package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @date 20/6/11 13:58
 * @des
 */
public class CheckoutResultListBean implements Serializable {
    private static final long serialVersionUID = 639973638306060114L;

    private String itemDetailsName;
    private String resultValue;
    private String unit;
    private String referenceValues;
    private String isNormal;
    private String range;
    private String seFlag;
    private String words;
    private String germName;
    private String growStatus;

    public String getItemDetailsName() {
        return itemDetailsName;
    }

    public void setItemDetailsName(String itemDetailsName) {
        this.itemDetailsName = itemDetailsName;
    }

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getReferenceValues() {
        return referenceValues;
    }

    public void setReferenceValues(String referenceValues) {
        this.referenceValues = referenceValues;
    }

    public String getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(String isNormal) {
        this.isNormal = isNormal;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getSeFlag() {
        return seFlag;
    }

    public void setSeFlag(String seFlag) {
        this.seFlag = seFlag;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getGermName() {
        return germName;
    }

    public void setGermName(String germName) {
        this.germName = germName;
    }

    public String getGrowStatus() {
        return growStatus;
    }

    public void setGrowStatus(String growStatus) {
        this.growStatus = growStatus;
    }
}
