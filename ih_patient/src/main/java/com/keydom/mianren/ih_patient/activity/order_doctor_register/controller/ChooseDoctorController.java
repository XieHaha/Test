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
import com.keydom.mianren.ih_patient.activity.order_doctor_register.view.ChooseDoctorView;
import com.keydom.mianren.ih_patient.bean.DateInfo;
import com.keydom.mianren.ih_patient.bean.DepartmentSchedulingBean;
import com.keydom.mianren.ih_patient.bean.HospitaldepartmentsInfo;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.net.OrderService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选择医生控制器
 */
public class ChooseDoctorController extends ControllerImpl<ChooseDoctorView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_doctor_tv:
                RegisterSearchActivity.start(getContext(), Type.SEARCHDOCTOR,
                        getView().getSelectedDepartmentId(), "", null);
                break;
            case R.id.llRightComplete:
                if (getView().isSelect()) {
                    getView().defineSelect();
                } else {
                    RegisterSearchActivity.start(getContext(), Type.SEARCHDOCTOR,
                            getView().getSelectedDepartmentId(), "", null);
                }
                break;
        }
    }

    /**
     * 查询全部医生
     */
    public void queryDateList(long deptId) {
        Map<String, Object> map = new HashMap<>();
        map.put("hospitalId", App.hospitalId);
        map.put("deptId", deptId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).getDateList(map), new HttpSubscriber<List<DateInfo>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<DateInfo> data) {
                getView().getDateListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                hideLoading();
                getView().getDateListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 查询全部科室
     */
    public void QueryDeptList(Map<String, Object> map) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).getDepartmentList(map), new HttpSubscriber<List<HospitaldepartmentsInfo>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<HospitaldepartmentsInfo> data) {
                hideLoading();
                getView().getDepartmentSuceess(data);
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
     * 查询排班医生列表
     */
    public void getDoctorList(String date, long deptId) {
        Map<String, Object> map = new HashMap<>();
        map.put("hospitalId", App.hospitalId);
        map.put("userId", Global.getUserId());
        map.put("date", date);
        map.put("deptId", deptId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).getDoctorList(map), new HttpSubscriber<List<DepartmentSchedulingBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<DepartmentSchedulingBean> data) {
                getView().getDoctorListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                hideLoading();
                getView().getDoctorListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
