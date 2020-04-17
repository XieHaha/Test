package com.keydom.mianren.ih_doctor.activity.online_triage.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.DiagnoseOrderDetailBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.ApiService;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_triage.view.TriageOrderDetailView;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.TriageApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 */
public class TriageOrderDetailController extends ControllerImpl<TriageOrderDetailView> implements View.OnClickListener {
    /**
     * 接收操作
     */
    private static final int RECEIVE_OPERATE = 1;
    /**
     * 退回操作
     */
    private static final int BACK_OPERATE = 0;

    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.triage_order_detail_bottom_right:
                receiveTriage(RECEIVE_OPERATE);
                break;
            default:
                break;
        }
    }


    /**
     * 问诊信息
     */
    public void getPatientInquisitionById(String orderId) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).getPatientInquisitionById(orderId), new HttpSubscriber<DiagnoseOrderDetailBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable DiagnoseOrderDetailBean data) {
                getView().getInquisitionDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getInquisitionDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 接收、拒绝操作
     *
     * @param option 操作类型
     */
    private void receiveTriage(int option) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(TriageApiService.class).doctorTriageConfirm(HttpService.INSTANCE.object2Body(getView().getOperateMap(option))), new HttpSubscriber<String>(getContext(), getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().operationSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().operationFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
