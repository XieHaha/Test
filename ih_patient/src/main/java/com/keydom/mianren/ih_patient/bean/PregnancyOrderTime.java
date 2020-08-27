package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

public class PregnancyOrderTime implements Serializable {
    private static final long serialVersionUID = 3516112206748892442L;


    /**
     * timeType : am
     * timeInterval : 8:00~8:30
     */

    private int count;
    private String timeInterval;

    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }
}
