package com.keydom.mianren.ih_doctor.activity.online_triage.controller;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.activity.online_triage.TriageOrderDetailActivity;
import com.keydom.mianren.ih_doctor.activity.online_triage.view.TriageOrderFragmentView;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.net.TriageApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @date 4月2日 15:03
 * 分诊列表
 */
public class TriageOrderFragmentController extends ControllerImpl<TriageOrderFragmentView> implements BaseQuickAdapter.OnItemClickListener {

    /**
     * 获取无痛分娩预约列表
     */
    public void getTriageOrderList(TypeEnum typeEnum) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(TriageApiService.class).triageOrderApplyList(getView().getParamsMap()), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        TriageOrderDetailActivity.startWithAction(mContext, position);
    }
}
