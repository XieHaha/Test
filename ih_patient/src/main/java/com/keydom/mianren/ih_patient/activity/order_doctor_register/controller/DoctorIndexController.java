package com.keydom.mianren.ih_patient.activity.order_doctor_register.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.view.DoctorIndexView;
import com.keydom.mianren.ih_patient.bean.DoctorInfo;
import com.keydom.mianren.ih_patient.net.ReservationService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 医生详情控制器
 */
public class DoctorIndexController extends ControllerImpl<DoctorIndexView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {

    }

    /**
     * 获取医生详情
     */
    public void getDoctorDetail(String doctorCode){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ReservationService.class).doctorDetails(doctorCode), new HttpSubscriber<DoctorInfo>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable DoctorInfo data) {
                getView().getDoctorDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getDoctorDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
