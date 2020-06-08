package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;


public class DrugUseConfigBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<FrequencyBean> frequencyList;
    private List<UseWayBean> wayList;
    private List<String> unitList;
    private List<String> dispensingUnitList;


    public List<FrequencyBean> getFrequencyList() {
        return frequencyList;
    }

    public void setFrequencyList(List<FrequencyBean> frequencyList) {
        this.frequencyList = frequencyList;
    }

    public List<UseWayBean> getWayList() {
        return wayList;
    }

    public void setWayList(List<UseWayBean> wayList) {
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
