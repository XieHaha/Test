package com.keydom.ih_doctor.activity.patient_manage.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.activity.patient_manage.view.ChoosePatientView;
import com.keydom.ih_doctor.bean.ImPatientInfo;
import com.keydom.ih_doctor.net.PatientManageApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChoosePatientController extends ControllerImpl<ChoosePatientView> {

    /**
     * 获取患者
     */
    public void getUserList() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PatientManageApiService.class).getRegList(getView().getListMap()), new HttpSubscriber<List<ImPatientInfo>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<ImPatientInfo> data) {
                hideLoading();
                getView().getUserListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getUserListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
