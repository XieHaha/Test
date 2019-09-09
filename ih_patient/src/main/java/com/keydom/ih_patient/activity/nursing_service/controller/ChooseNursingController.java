package com.keydom.ih_patient.activity.nursing_service.controller;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.activity.nursing_service.view.ChooseNursingView;
import com.keydom.ih_patient.bean.ChooseNursingBean;
import com.keydom.ih_patient.net.NursingService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 选择护理控制器
 */
public class ChooseNursingController extends ControllerImpl<ChooseNursingView> {
    public void getNursingProject(){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingService.class).getAllNurseServiceCategoryByHospitalId(App.hospitalId), new HttpSubscriber<List<ChooseNursingBean>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable List<ChooseNursingBean> data) {
                hideLoading();
                getView().getTypeSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                getView().getTypeFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
