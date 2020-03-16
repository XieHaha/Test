package com.keydom.mianren.ih_doctor.activity.online_diagnose.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.view.DiagnosePatientInfoView;
import com.keydom.mianren.ih_doctor.bean.DiagnosePatientInfoBean;
import com.keydom.mianren.ih_doctor.net.DiagnoseApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class DiagnosePatientInfoController extends ControllerImpl<DiagnosePatientInfoView> {


    /**
     * 获取就诊人信息
     * @param id 就诊人ID
     */
    public void getUserInfo(long id) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getconsultRecord(id), new HttpSubscriber<DiagnosePatientInfoBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable DiagnosePatientInfoBean data) {
                getView().getPatientInfoSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getPatientInfoFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
