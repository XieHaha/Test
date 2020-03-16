package com.keydom.mianren.ih_patient.bean.entity;

import com.keydom.mianren.ih_patient.bean.entity.pharmacy.PrescriptionItemEntity;

import java.io.Serializable;
import java.util.List;

public class PutDrugBean implements Serializable {
    String startLonLat;  //经纬度
    List<PrescriptionItemEntity>drugs; //药品信息

    public String getStartLonLat() {
        return startLonLat;
    }

    public void setStartLonLat(String startLonLat) {
        this.startLonLat = startLonLat;
    }


    public List<PrescriptionItemEntity> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<PrescriptionItemEntity> drugs) {
        this.drugs = drugs;
    }


    @Override
    public String toString() {
        return "PutDrugBean{" +
                "startLonLat='" + startLonLat + '\'' +
                ", drugs=" + drugs +
                '}';
    }
}
