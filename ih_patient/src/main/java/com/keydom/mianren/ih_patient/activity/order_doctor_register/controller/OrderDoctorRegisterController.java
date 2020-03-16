package com.keydom.mianren.ih_patient.activity.order_doctor_register.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.RegisterSearchActivity;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.view.OrderDoctorRegisterView;
import com.keydom.mianren.ih_patient.bean.HospitalAreaInfo;
import com.keydom.mianren.ih_patient.bean.HospitaldepartmentsInfo;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.net.OrderService;
import com.keydom.mianren.ih_patient.utils.DepartmentDataHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * 预约挂号控制器
 */
public class OrderDoctorRegisterController extends ControllerImpl<OrderDoctorRegisterView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_layout:
                getView().showHospitalAreaPopupWindow();
                break;
            case R.id.max_search_layout:
            case R.id.search_layout:
                RegisterSearchActivity.start(getContext(), Type.SEARCHDOCTORANDDEPARTMENTS, 1, ""
                        , getView().getDepartmentList());
                break;
            case R.id.llRightComplete:
                RegisterSearchActivity.start(getContext(), Type.SEARCHDOCTORANDDEPARTMENTS, 1, ""
                        , getView().getDepartmentList());
                break;
        }
    }

    /**
     * 查询科室
     */
    public void QueryDataList(Map<String, Object> map) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).getDepartmentList(map), new HttpSubscriber<List<HospitaldepartmentsInfo>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<HospitaldepartmentsInfo> data) {
                hideLoading();
                getView().upLoadData(DepartmentDataHelper.getDataAfterHandle(data), data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                hideLoading();
                getView().getDepartmentFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 查询医院
     */
    public void queryHospitalAreaList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).findHospitalAreaList(App.hospitalId), new HttpSubscriber<List<HospitalAreaInfo>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable List<HospitalAreaInfo> data) {
                getView().getAreaList(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getAreaListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
