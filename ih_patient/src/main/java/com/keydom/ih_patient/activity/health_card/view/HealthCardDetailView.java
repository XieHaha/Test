package com.keydom.ih_patient.activity.health_card.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.HealthCardResponse;

import java.util.List;

public interface HealthCardDetailView extends BaseView {


    void queryCardSuccess(List<HealthCardResponse> data);

    void queryCardFailed(String msg);


    void getQrCodeSuccess(List<HealthCardResponse> data);

    void getQrCodeFailed(String msg);
}
