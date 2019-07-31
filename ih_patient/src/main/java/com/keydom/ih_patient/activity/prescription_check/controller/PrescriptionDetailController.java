package com.keydom.ih_patient.activity.prescription_check.controller;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.activity.prescription_check.view.PrescriptionDetailView;
import com.keydom.ih_patient.bean.PrescriptionDetailBean;
import com.keydom.ih_patient.net.PrescriptionService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * created date: 2019/1/18 on 14:31
 * des:处方详情控制器
 */
public class PrescriptionDetailController extends ControllerImpl<PrescriptionDetailView> {
    /**
     * 获取处方详情
     */
    public void getPrescriptionDetail(String id){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getDetailById(id), new HttpSubscriber<PrescriptionDetailBean>(getContext(),getDisposable(),true,true) {
            @Override
            public void requestComplete(@Nullable PrescriptionDetailBean data) {
                getView().getDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
