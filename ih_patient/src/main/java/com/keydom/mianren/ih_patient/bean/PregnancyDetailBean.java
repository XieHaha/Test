package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

public class PregnancyDetailBean implements Serializable {
    private static final long serialVersionUID = 2741902668047570010L;


    /**
     * date : 1月16日
     * showDate : 13周6天
     * context : 宝宝现在大小像西红柿了，十分活跃，但你还难感受到他动，主要因为他肌肉能量太小了
     * pregnantDay : 97
     * dueDay : 183
     * babyLength : 32.1
     * babyWeight : 210
     */

    private String date;
    private String showDate;
    private String context;
    private String pregnantDay;
    private String dueDay;
    private String babyLength;
    private String babyWeight;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getPregnantDay() {
        return pregnantDay;
    }

    public void setPregnantDay(String pregnantDay) {
        this.pregnantDay = pregnantDay;
    }

    public String getDueDay() {
        return dueDay;
    }

    public void setDueDay(String dueDay) {
        this.dueDay = dueDay;
    }

    public String getBabyLength() {
        return babyLength;
    }

    public void setBabyLength(String babyLength) {
        this.babyLength = babyLength;
    }

    public String getBabyWeight() {
        return babyWeight;
    }

    public void setBabyWeight(String babyWeight) {
        this.babyWeight = babyWeight;
    }
}
