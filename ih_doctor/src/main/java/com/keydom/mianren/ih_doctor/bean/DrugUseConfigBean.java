package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;


public class DrugUseConfigBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<String> frequencyList;
    private List<String> wayList;
    private List<String> unitList;
    private List<String> dispensingUnitList;

    public List<String> getFrequencyList() {
        return frequencyList;
    }

    public void setFrequencyList(List<String> frequencyList) {
        this.frequencyList = frequencyList;
    }

    public List<String> getWayList() {
        return wayList;
    }

    public void setWayList(List<String> wayList) {
        this.wayList = wayList;
    }

    public List<String> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<String> unitList) {
        this.unitList = unitList;
    }

    public List<String> getDispensingUnitList() {
        return dispensingUnitList;
    }

    public void setDispensingUnitList(List<String> dispensingUnitList) {
        this.dispensingUnitList = dispensingUnitList;
    }
}
