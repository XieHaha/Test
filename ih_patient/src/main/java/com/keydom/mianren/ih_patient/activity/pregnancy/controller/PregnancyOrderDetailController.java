package com.keydom.mianren.ih_patient.activity.pregnancy.controller;


import android.support.annotation.Nullable;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.pregnancy.view.PregnancyOrderDetailView;
import com.keydom.mianren.ih_patient.bean.PregnancyOrderBean;
import com.keydom.mianren.ih_patient.net.PregnancyService;

import org.jetbrains.annotations.NotNull;

public class PregnancyOrderDetailController extends ControllerImpl<PregnancyOrderDetailView> implements View.OnClickListener {


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }





    /**
     * 获取产检详情
     */
    public void getPregnancyOrderDetails(String recordId) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PregnancyService.class).getDetailProductInspection(recordId), new HttpSubscriber<PregnancyOrderBean>(getContext(), getDisposable(), false, false) {
            @Override
            public void requestComplete(@Nullable PregnancyOrderBean data) {
                if (data != null) {
                    getView().getDetailProductInspectionSuccess(data);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getDetailProductInspectionFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
