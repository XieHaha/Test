package com.keydom.mianren.ih_patient.activity.child_health.controller;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.activity.child_health.ChildHealthDetailActivity;
import com.keydom.mianren.ih_patient.activity.child_health.view.ChildHealthProjectView;
import com.keydom.mianren.ih_patient.bean.ChildHealthProjectBean;
import com.keydom.mianren.ih_patient.net.ChildHealthService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author 顿顿
 * @date 20/2/27 11:39
 * @des 儿童保健项目列表
 */
public class ChildHealthProjectController extends ControllerImpl<ChildHealthProjectView> implements BaseQuickAdapter.OnItemClickListener {

    /**
     * 儿童保健项目列表
     */
    public void childProjectList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChildHealthService.class).childProjectList(), new HttpSubscriber<List<ChildHealthProjectBean>>(getContext(), getDisposable(), true, false) {

            @Override
            public void requestComplete(@Nullable List<ChildHealthProjectBean> data) {
                getView().requestProjectSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException e, int code, @NotNull String msg) {
                getView().requestProjectFailed(msg);
                return super.requestError(e, code, msg);

            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ChildHealthProjectBean projectBean = (ChildHealthProjectBean) adapter.getItem(position);
        ChildHealthDetailActivity.start(getContext(),projectBean);
    }
}
