package com.keydom.mianren.ih_doctor.activity.inspection_report.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.activity.inspection_report.view.BodyCheckDetailView;
import com.keydom.mianren.ih_doctor.bean.BodyCheckDetailInfo;
import com.keydom.mianren.ih_doctor.net.DiagnoseApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BodyCheckDetailController extends ControllerImpl<BodyCheckDetailView> {

    /**
     * 获取检查申请详情
     *
     * @param applyNumber 检查报告单号
     */
    public void getBodyCheckDetail(String applyNumber) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getInspectResultInfo(applyNumber), new HttpSubscriber<BodyCheckDetailInfo>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable BodyCheckDetailInfo data) {
                hideLoading();
                getView().getBodyCheckDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getBodyCheckDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
