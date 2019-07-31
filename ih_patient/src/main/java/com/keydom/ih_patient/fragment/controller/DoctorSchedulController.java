package com.keydom.ih_patient.fragment.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.bean.DoctorSchedulingBean;
import com.keydom.ih_patient.fragment.view.DoctorSchedulView;
import com.keydom.ih_patient.net.OrderService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医生排班表控制器
 */
public class DoctorSchedulController extends ControllerImpl<DoctorSchedulView> {
    /**
     * 查询医生排班表
     */
    public void getDoctorSchedulingList(long deptId,long deptUserId){
        Map<String,Object> map=new HashMap<>();
        map.put("hospitalId", App.hospitalId);
        map.put("deptId",deptId);
        map.put("deptUserId",deptUserId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).getDoctorSchedul(map), new HttpSubscriber<List<DoctorSchedulingBean>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<DoctorSchedulingBean> data) {
                getView().getDoctorSchedulingListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getDoctorSchedulingListFailde(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
