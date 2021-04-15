package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.activity.health_manager.HealthConsultantDetailActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthConsultantView;
import com.keydom.mianren.ih_patient.bean.HealthConsultantBean;
import com.keydom.mianren.ih_patient.net.HealthManagerService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 健康管理咨询师
 */
public class HealthConsultantController extends ControllerImpl<HealthConsultantView> implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        HealthConsultantBean bean = (HealthConsultantBean) adapter.getItem(position);
        HealthConsultantDetailActivity.start(getContext(), bean);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        HealthConsultantBean bean = (HealthConsultantBean) adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putBoolean("consultType", true);
        bundle.putString("title", bean.getName());
        ImClient.startConversation(mContext, bean.getUserCode(), bundle);

        updateConsultTimeAndRecord(bean.getId());
    }

    /**
     * 获取可咨询医生列表
     */
    public void healthDoctorList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HealthManagerService.class).healthDoctorList(), new HttpSubscriber<List<HealthConsultantBean>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable List<HealthConsultantBean> data) {
                getView().requestHealthDoctorListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 修改咨询记录
     */
    public void updateConsultTimeAndRecord(long doctorId) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HealthManagerService.class).updateConsultTimeAndRecord(doctorId, getView().getPatientId(), 0), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                healthDoctorList();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }
}
