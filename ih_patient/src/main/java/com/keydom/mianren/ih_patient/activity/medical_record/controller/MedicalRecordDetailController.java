package com.keydom.mianren.ih_patient.activity.medical_record.controller;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.medical_record.view.MedicalRecordDetailView;
import com.keydom.mianren.ih_patient.bean.DiagnoseCaseBean;
import com.keydom.mianren.ih_patient.net.DiagnoseApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * created date: 2019/1/4 on 16:49
 * des:处方详情控制器
 */
public class MedicalRecordDetailController extends ControllerImpl<MedicalRecordDetailView> {
    /**
     * 获取电子处方详情
     */
    public void getMedicalDetail(long id){
        Map<String, Object> map = new HashMap<>();
        map.put("medicalId", id);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getMedicalRecordInfo(map), new HttpSubscriber<DiagnoseCaseBean>(getContext(),getDisposable(),true,false) {
            @Override
            public void requestComplete(@Nullable DiagnoseCaseBean data) {
                getView().getDetailCallBack(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
