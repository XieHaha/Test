package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.InterventionPlanDetailActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.view.InterventionPlanView;
import com.keydom.mianren.ih_patient.bean.InterventionPlanBean;
import com.keydom.mianren.ih_patient.net.HealthManagerService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 干预方案
 */
public class InterventionPlanController extends ControllerImpl<InterventionPlanView> implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            getView().finishPage();
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        InterventionPlanBean planBean = (InterventionPlanBean) adapter.getItem(position);
        InterventionPlanDetailActivity.start(getContext(),getView().getPatientId(), planBean.getId());
    }


    /**
     * 获取干预方案列表
     */
    public void interventionPlanList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HealthManagerService.class).interventionPlanList(HttpService.INSTANCE.object2Body(getView().getParams())), new HttpSubscriber<PageBean<InterventionPlanBean>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable PageBean<InterventionPlanBean> data) {
                getView().requestInterventionPlanListSuccess(data.getRecords());
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().requestInterventionPlanListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
