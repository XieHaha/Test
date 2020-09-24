package com.keydom.mianren.ih_patient.activity.card_operate.controller;


import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.card_operate.view.ElectronicCardDetailView;
import com.keydom.mianren.ih_patient.net.HealthCardService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 卡控制器
 *
 * @author 顿顿
 */
public class ElectronicCardDetailController extends ControllerImpl<ElectronicCardDetailView> {
    /**
     * 查询电子健康卡
     */
    public void queryHealthCardDetail() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HealthCardService.class).queryHealthCardDetail(HttpService.INSTANCE.object2Body(getView().getParams())), new HttpSubscriber<String>(getContext(), getDisposable(), true, false) {

            @Override
            public void requestComplete(@Nullable String data) {
                getView().queryHealthCardSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().queryHealthCardFailed(msg);
                return super.requestError(exception, code, msg);

            }
        });
    }
}
