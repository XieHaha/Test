package com.keydom.ih_patient.activity.health_card.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.activity.health_card.view.HealthCardDetailView;
import com.keydom.ih_patient.bean.HealthCardResponse;
import com.keydom.ih_patient.net.HealthCardService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HealthCardDetailController extends ControllerImpl<HealthCardDetailView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {

    }


    /**
     * 查询绑定健康卡信息
     */
    public void queryCard() {
        Map<String, String> map = new HashMap<>();
        map.put("name", App.userInfo.getCertificationName());
        map.put("idNo", App.userInfo.getIdCard());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HealthCardService.class).queryCard(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<List<HealthCardResponse>>(getContext(), getDisposable(), true,false) {
            @Override
            public void requestComplete(@Nullable List<HealthCardResponse> data) {
                getView().queryCardSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().queryCardFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }



    /**
     * 查询 健康卡 二维码
     */
    public void getHealthCardState(String healthId) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HealthCardService.class).applyText(healthId), new HttpSubscriber<List<HealthCardResponse>>(getContext(), getDisposable(), true, false) {

            @Override
            public void requestComplete(@Nullable List<HealthCardResponse> data) {
                getView().getQrCodeSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getQrCodeFailed(msg);
                return super.requestError(exception, code, msg);

            }
        });
    }
}
