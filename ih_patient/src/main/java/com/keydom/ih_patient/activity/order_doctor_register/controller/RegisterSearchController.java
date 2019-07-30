package com.keydom.ih_patient.activity.order_doctor_register.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.activity.order_doctor_register.view.RegisterSearchView;
import com.keydom.ih_patient.bean.DepartmentSchedulingBean;
import com.keydom.ih_patient.bean.DocAndDepSearchBean;
import com.keydom.ih_patient.net.OrderService;
import com.keydom.ih_patient.utils.DepartmentDataHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * 查询科室
 */
public class RegisterSearchController extends ControllerImpl<RegisterSearchView> {

    /**
     * 查询科室下医生
     */
    public void searchDoctor(Map<String,Object> map){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).searchDoctor(map), new HttpSubscriber<List<DepartmentSchedulingBean>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<DepartmentSchedulingBean> data) {
                getView().getSearchDocAndDepSuccess(DepartmentDataHelper.getSearchDocAfterHandle(data));
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 模糊查询科室或者医生
     */
    public void searchDoctorOrDepartments(Map<String,Object> map){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).searchDoctorOrDepartments(map), new HttpSubscriber<DocAndDepSearchBean>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable DocAndDepSearchBean data) {
                getView().getSearchDocAndDepSuccess(DepartmentDataHelper.getSearchDocDepAfterHandle(data));
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getSearchDocAndDepFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
