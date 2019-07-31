package com.keydom.ih_doctor.activity.doctor_cooperation.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.activity.doctor_cooperation.view.DianoseCaseDetailView;
import com.keydom.ih_doctor.bean.DiagnoseCaseBean;
import com.keydom.ih_doctor.net.GroupCooperateApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class DianoseCaseDetailController extends ControllerImpl<DianoseCaseDetailView> {
    /**
     * 获取病历详情
     */
    public void getDetail() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(GroupCooperateApiService.class).getMedicalRecordInfo(getView().getQueryMap()), new HttpSubscriber<DiagnoseCaseBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable DiagnoseCaseBean data) {
                getView().getDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
