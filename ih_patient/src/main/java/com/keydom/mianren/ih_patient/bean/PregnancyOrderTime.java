package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

public class PregnancyOrderTime implements Serializable {
    private static final long serialVersionUID = 3516112206748892442L;


    /**
     * timeType : am
     * timeInterval : 8:00~8:30
     */

    private String timeType;
    private String timeInterval;

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }
}
