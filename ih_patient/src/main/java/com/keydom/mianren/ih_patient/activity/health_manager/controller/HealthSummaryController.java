package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.activity.health_manager.HealthSummaryDetailActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthSummaryView;
import com.keydom.mianren.ih_patient.bean.HealthSummaryBean;
import com.keydom.mianren.ih_patient.net.HealthManagerService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 健康总结
 */
public class HealthSummaryController extends ControllerImpl<HealthSummaryView> implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        HealthSummaryBean summaryBean = (HealthSummaryBean) adapter.getItem(position);
        HealthSummaryDetailActivity.start(getContext(), summaryBean.getId(),
                getView().getPatientId());
    }

    /**
     * 获取健康总结列表
     */
    public void patientHealthConclusionList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HealthManagerService.class).patientHealthConclusionList(HttpService.INSTANCE.object2Body(getView().getParams())), new HttpSubscriber<PageBean<HealthSummaryBean>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable PageBean<HealthSummaryBean> data) {
                getView().requestHealthSummaryListSuccess(data.getRecords());
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
