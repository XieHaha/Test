package com.keydom.mianren.ih_patient.activity.inspection_report.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.inspection_report.view.BodyCheckDetailView;
import com.keydom.mianren.ih_patient.bean.BodyCheckDetailInfo;
import com.keydom.mianren.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 检查单控制器
 */
public class BodyCheckDetailController extends ControllerImpl<BodyCheckDetailView> {
    /**
     * 查询检查单详情接口
     */
    public void getBodyCheckDetail(String applyNumber){
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getInspectResultInfo(applyNumber), new HttpSubscriber<BodyCheckDetailInfo>(getContext(),getDisposable(),false) {
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
