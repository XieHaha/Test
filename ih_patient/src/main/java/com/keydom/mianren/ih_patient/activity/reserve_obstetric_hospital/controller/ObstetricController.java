package com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.view.ObstetricView;
import com.keydom.mianren.ih_patient.bean.DepartmentInfo;
import com.keydom.mianren.ih_patient.net.HospitalAppointmentService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 住院记录
 *
 * @author 顿顿
 */
public class ObstetricController extends ControllerImpl<ObstetricView> {

    /**
     * 住院记录
     */
    public void getObsByCardNo(int type, String eleCardNo) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HospitalAppointmentService.class).getObsByCardNo(eleCardNo, type), new HttpSubscriber<List<DepartmentInfo>>() {
            @Override
            public void requestComplete(@Nullable List<DepartmentInfo> data) {
                getView().getObstetricListSuccess(null, null);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }
}
