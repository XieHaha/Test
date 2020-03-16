package com.keydom.mianren.ih_doctor.activity.nurse_service.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.activity.nurse_service.view.ChooseNursingView;
import com.keydom.mianren.ih_doctor.bean.CategoryBean;
import com.keydom.mianren.ih_doctor.net.NurseServiceApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChooseNursingController extends ControllerImpl<ChooseNursingView> {


    public void getNurseServiceType() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).getAllNurseServiceCategoryByHospitalId(MyApplication.userInfo.getHospitalId()), new HttpSubscriber<List<CategoryBean>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable List<CategoryBean> data) {
                getView().getCotegorySuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getCotegoryFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
