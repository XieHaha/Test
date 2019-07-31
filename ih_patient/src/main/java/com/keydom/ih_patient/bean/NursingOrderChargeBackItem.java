package com.keydom.ih_patient.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_patient.adapter.NursingChargeBackAdapter;

/**
 * created date: 2018/12/24 on 11:06
 * des:护理退单item
 */
public class NursingOrderChargeBackItem implements MultiItemEntity{
    private String serviceName;
    private String servicePercent;
    private String serviceFee;
    private boolean canChargeBack;

    public boolean isCanChargeBack() {
        return canChargeBack;
    }

    public void setCanChargeBack(boolean canChargeBack) {
        this.canChargeBack = canChargeBack;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePercent() {
        return servicePercent;
    }

    public void setServicePercent(String servicePercent) {
        this.servicePercent = servicePercent;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    @Override
    public int getItemType() {
        return NursingChargeBackAdapter.TYPE_3;
    }
}
