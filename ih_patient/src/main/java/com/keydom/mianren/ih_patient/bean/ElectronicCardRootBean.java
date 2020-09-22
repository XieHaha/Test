package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 顿顿
 * @date 20/9/22 17:29
 * @des
 */
public class ElectronicCardRootBean implements Serializable {
    private static final long serialVersionUID = -9178751367318886563L;

    private List<MedicalCardInfo> mine;
    private List<MedicalCardInfo> others;

    public List<MedicalCardInfo> getMine() {
        return mine;
    }

    public void setMine(List<MedicalCardInfo> mine) {
        this.mine = mine;
    }

    public List<MedicalCardInfo> getOthers() {
        return others;
    }

    public void setOthers(List<MedicalCardInfo> others) {
        this.others = others;
    }
}
