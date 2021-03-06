package com.keydom.mianren.ih_patient.activity.inspection_report.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.inspection_report.view.InspectionDetailView;
import com.keydom.mianren.ih_patient.bean.InspectionDetailBean;
import com.keydom.mianren.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 检查控制器
 */
public class BodyCheckDetailController extends ControllerImpl<InspectionDetailView> {

    /**
     * 获取检查详情
     */
    public void getInspectionDetail(String reportID) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getCheckoutResultInfo(reportID, 2), new HttpSubscriber<InspectionDetailBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable InspectionDetailBean data) {
                getView().getInspectionDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getInspectionDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
