package com.keydom.mianren.ih_patient.activity.nursing_service.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.nursing_service.view.NursingProjectDetailView;
import com.keydom.mianren.ih_patient.bean.NursingProjectInfo;
import com.keydom.mianren.ih_patient.net.NursingService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 护理项目详情控制器
 */
public class NursingProjectDetailController extends ControllerImpl<NursingProjectDetailView> {
    /**
     * 获取护理项目详情
     */
    public void getNurseServiceProjectDetailById(String nursingProjectId){
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingService.class).getNurseServiceProjectDetailById(nursingProjectId), new HttpSubscriber<NursingProjectInfo>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable NursingProjectInfo data) {
                hideLoading();
                getView().getNursingProjectDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getNursingProjectDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
