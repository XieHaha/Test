package com.keydom.ih_doctor.activity.online_diagnose.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.activity.online_diagnose.view.PrescriptionTempletDetailView;
import com.keydom.ih_doctor.bean.PrescriptionDrugDetailBean;
import com.keydom.ih_doctor.net.DiagnoseApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PrescriptionTempletDetailController extends ControllerImpl<PrescriptionTempletDetailView> {


    /**
     * 处方模板明细列表
     *
     * @param id 处方模板ID
     */
    public void getPrescriptionTemplateItemList(long id) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getPrescriptionTemplateItemList(id), new HttpSubscriber<PrescriptionDrugDetailBean>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable PrescriptionDrugDetailBean data) {
                getView().getTempletDetailListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getTempletDetailListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });


    }


}
