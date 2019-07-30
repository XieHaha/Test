package com.keydom.ih_patient.activity.order_examination.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_examination.view.ExaminationDateChooseView;
import com.keydom.ih_patient.bean.DateInfo;
import com.keydom.ih_patient.bean.HospitalAreaInfo;
import com.keydom.ih_patient.bean.SoruInfo;
import com.keydom.ih_patient.net.OrderService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查预约控制器
 */
public class ExaminationDateChooseController extends ControllerImpl<ExaminationDateChooseView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.hospital_area_layout:
                getView().showHospitalAreaPopupWindow();
                break;
        }
    }

    /**
     * 查询全部医院
     */
    public void queryHospitalAreaList(){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).findHospitalAreaList(App.hospitalId), new HttpSubscriber<List<HospitalAreaInfo>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<HospitalAreaInfo> data) {
                getView().getAreaList(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 查询全部检查
     */
    public void queryDateList(long hospitalAreaId){
        Map<String,Object> map=new HashMap<>();
        map.put("hospitalId",App.hospitalId);
        map.put("hospitalAreaId",hospitalAreaId);
        map.put("inspectProjectId",getView().getSelectedProjectId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).findSoruDate(map), new HttpSubscriber<List<DateInfo>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<DateInfo> data) {
                getView().getDateList(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getDateListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 查询检查详情
     */
    public void queryfindSoruInfoList(String selectedDate,long hospitalAreaId){
        Map<String,Object> map=new HashMap<>();
        map.put("hospitalId",App.hospitalId);
        map.put("inspectProjectId",getView().getSelectedProjectId());
        map.put("inspectDate",selectedDate);
        map.put("hospitalAreaId",hospitalAreaId);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).findSoruInfo(map), new HttpSubscriber<SoruInfo>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable SoruInfo data) {
                hideLoading();
                getView().getSoruInfo(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getSoruInfoFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
