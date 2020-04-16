package com.keydom.mianren.ih_doctor.activity.online_triage.controller;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_doctor.activity.online_triage.TriageOrderDetailActivity;
import com.keydom.mianren.ih_doctor.activity.online_triage.view.TriageOrderListView;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;

/**
 * @date 20/3/21 13:54
 * @des 分诊列表controller
 */
public class TriageOrderListController extends ControllerImpl<TriageOrderListView> implements BaseQuickAdapter.OnItemClickListener {

    /**
     * 获取无痛分娩预约列表
     */
    public void getTriageOrderList(TypeEnum typeEnum) {
//        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(TriageApiService.class).triageOrderApplyList(), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
//            @Override
//            public void requestComplete(@Nullable String data) {
//            }
//
//            @Override
//            public boolean requestError(@NotNull ApiException exception, int code,
//                                        @NotNull String msg) {
//                return super.requestError(exception, code, msg);
//            }
//        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        TriageOrderDetailActivity.startWithAction(mContext, position);
    }
}
