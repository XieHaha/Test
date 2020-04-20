package com.keydom.mianren.ih_doctor.activity.online_triage.controller;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.bean.TriageBean;
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
     * 分诊列表
     */
    public void getTriageOrderList(TypeEnum typeEnum) {
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(TriageApiService.class).triageOrderApplyList(getView().getParamsMap()), new HttpSubscriber<PageBean<TriageBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PageBean<TriageBean> data) {
                getView().getDataSuccess(typeEnum, data.getRecords());
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getDataFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        TriageOrderDetailActivity.startWithAction(mContext, (TriageBean) adapter.getItem(position),getView().getType());
    }
}
