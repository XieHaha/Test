package com.keydom.mianren.ih_doctor.activity.patient_manage.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.activity.patient_manage.view.WarrantListView;
import com.keydom.mianren.ih_doctor.bean.WarrantDoctorBean;
import com.keydom.mianren.ih_doctor.net.PatientManageApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WarrantListController extends ControllerImpl<WarrantListView> {

    public void getWarrantList(){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PatientManageApiService.class).getAuthorizedList(MyApplication.userInfo.getId()), new HttpSubscriber<List<WarrantDoctorBean>>() {
            @Override
            public void requestComplete(@Nullable List<WarrantDoctorBean> data) {
                getView().getWarrantListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getWarrantListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
