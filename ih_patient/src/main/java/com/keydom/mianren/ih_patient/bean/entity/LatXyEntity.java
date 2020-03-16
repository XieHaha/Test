package com.keydom.mianren.ih_patient.bean.entity;

import java.io.Serializable;

public class LatXyEntity implements Serializable {

    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "LatXyEntity{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
