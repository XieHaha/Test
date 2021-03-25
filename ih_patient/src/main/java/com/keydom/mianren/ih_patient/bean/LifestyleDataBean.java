package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @author 顿顿
 * @date 21/3/25 11:20
 * @des
 */
public class LifestyleDataBean implements Serializable {
    private static final long serialVersionUID = 1786641033710967731L;

    private boolean selected;


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void changeSelectStatus() {
        setSelected(!isSelected());
    }
}
