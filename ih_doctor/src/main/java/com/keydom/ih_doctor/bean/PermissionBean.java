package com.keydom.ih_doctor.bean;

import java.io.Serializable;

public class PermissionBean implements Serializable {
    private int buildingGroupState;
    private int empowerState;

    public int getBuildingGroupState() {
        return buildingGroupState;
    }

    public void setBuildingGroupState(int buildingGroupState) {
        this.buildingGroupState = buildingGroupState;
    }

    public int getEmpowerState() {
        return empowerState;
    }

    public void setEmpowerState(int empowerState) {
        this.empowerState = empowerState;
    }
}
