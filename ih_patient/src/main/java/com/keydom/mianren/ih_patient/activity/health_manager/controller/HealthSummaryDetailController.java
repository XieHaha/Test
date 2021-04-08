package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthSummaryDetailView;
import com.keydom.mianren.ih_patient.bean.HealthSummaryBean;
import com.keydom.mianren.ih_patient.net.HealthManagerService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 健康报告详情
 */
public class HealthSummaryDetailController extends ControllerImpl<HealthSummaryDetailView> implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.health_summary_detail_next_tv) {

        }
    }

    /**
     * 获取健康报告详情
     */
    public void patientHealthConclusionDetail(String consultRecordId) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HealthManagerService.class).patientHealthConclusionDetail(consultRecordId), new HttpSubscriber<HealthSummaryBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable HealthSummaryBean data) {
                getView().requestSummaryDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
