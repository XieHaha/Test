package com.keydom.mianren.ih_patient.activity.order_triage.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.DiagnoseOrderDetailBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.ApiService;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.order_triage.view.TriageOrderDetailView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 */
public class TriageOrderDetailController extends ControllerImpl<TriageOrderDetailView> {

    /**
     * 问诊信息
     */
    public void getPatientInquisitionById(String orderId) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).getPatientInquisitionById(orderId), new HttpSubscriber<DiagnoseOrderDetailBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable DiagnoseOrderDetailBean data) {
                getView().getInquisitionDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getInquisitionDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
