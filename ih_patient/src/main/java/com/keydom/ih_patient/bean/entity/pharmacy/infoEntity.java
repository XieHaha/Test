package com.keydom.ih_patient.bean.entity.pharmacy;

import java.io.Serializable;

public class infoEntity implements Serializable {


    private String  AcceptTime;
    private String AcceptStation;


    public String getAcceptTime() {
        return AcceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        AcceptTime = acceptTime;
    }

    public String getAcceptStation() {
        return AcceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        AcceptStation = acceptStation;
    }

    @Override
    public String toString() {
        return "infoEntity{" +
                "AcceptTime='" + AcceptTime + '\'' +
                ", AcceptStation='" + AcceptStation + '\'' +
                '}';
    }
}
