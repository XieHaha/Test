package com.keydom.mianren.ih_patient.activity.inspection_report.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.inspection_report.view.BodyCheckDetailView;
import com.keydom.mianren.ih_patient.bean.InspectionDetailBean;
import com.keydom.mianren.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 检查单控制器
 */
public class BodyCheckDetailController extends ControllerImpl<BodyCheckDetailView> {
    /**
     * 获取检验详情
     */
    public void getInspectionDetail(String reportID) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getCheckoutResultInfo(reportID, 1), new HttpSubscriber<InspectionDetailBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable InspectionDetailBean data) {
                getView().getBodyCheckDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getBodyCheckDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
