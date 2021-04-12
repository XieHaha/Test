package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.HealthConsultantActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.view.InterventionPlanDetailView;
import com.keydom.mianren.ih_patient.bean.InterventionPlanBean;
import com.keydom.mianren.ih_patient.net.HealthManagerService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 干预方案
 */
public class InterventionPlanDetailController extends ControllerImpl<InterventionPlanDetailView> implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.intervention_plan_detail_contact_tv) {
            HealthConsultantActivity.start(getContext(), getView().getPatientId());
        }
    }

    /**
     * 获取干预方案详情
     */
    public void interventionPlanDetail(String planId) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HealthManagerService.class).interventionPlanDetail(planId), new HttpSubscriber<InterventionPlanBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable InterventionPlanBean data) {
                getView().requestInterventionPlanDetailSuccess(data);
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
