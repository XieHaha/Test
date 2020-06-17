package com.keydom.mianren.ih_patient.activity.payment_records.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.activity.payment_records.view.PaiedRecordView;
import com.keydom.mianren.ih_patient.bean.PayRecordBean;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.PayService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 已缴费控制器
 */
public class PaiedRecordController extends ControllerImpl<PaiedRecordView> {
    /**
     * 获取缴费记录
     */
    public void getConsultationPayList(SmartRefreshLayout refreshLayout, int state) {
        Map<String, Object> map = new HashMap<>();
        map.put("patientId", getView().getPatientId());
        map.put("hospitalId", App.hospitalId);
        map.put("registerUserId", Global.getUserId());
        map.put("state", state);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PayService.class).getConsultationPayList(map), new HttpSubscriber<List<PayRecordBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<PayRecordBean> data) {
                if (data != null) {
                    getView().paymentListCallBack(data);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                if (!"token解析失败".equals(msg)) {
                    //                    ToastUtils.showLong(msg);
                }
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                return super.requestError(exception, code, msg);
            }
        });

    }
}
