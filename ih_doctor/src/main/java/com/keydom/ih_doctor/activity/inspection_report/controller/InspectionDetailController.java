package com.keydom.ih_doctor.activity.inspection_report.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.activity.inspection_report.view.InspectionDetailView;
import com.keydom.ih_doctor.bean.InspectionDetailInof;
import com.keydom.ih_doctor.net.DiagnoseApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InspectionDetailController extends ControllerImpl<InspectionDetailView> {

    /**
     * 获取检验报告单详情
     *
     * @param applyNumber 检验报告单ID
     */
    public void getInspectionDetail(String applyNumber) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getCheckoutResultInfo(applyNumber), new HttpSubscriber<InspectionDetailInof>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable InspectionDetailInof data) {
                hideLoading();
                getView().getInspectionDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getInspectionDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
