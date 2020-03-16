package com.keydom.mianren.ih_doctor.activity.doctor_cooperation.controller;

import android.app.Activity;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.view.DiagnoseRecoderView;
import com.keydom.mianren.ih_doctor.bean.DiagnoseRecoderBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.GroupCooperateApiService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class DiagnoseRecoderController extends ControllerImpl<DiagnoseRecoderView> implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_tv) {
            showLoading();
            setDefaultPage();
            getInquisitionRecordByDocId(TypeEnum.REFRESH);
            CommonUtils.hideSoftKeyboard((Activity) getContext());
        }
    }


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        getInquisitionRecordByDocId(TypeEnum.LOAD_MORE);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setDefaultPage();
        getInquisitionRecordByDocId(TypeEnum.REFRESH);
    }


    /**
     * 获取问诊记录列表
     *
     * @param type 判断是刷新还是加载更多
     */
    public void getInquisitionRecordByDocId(final TypeEnum type) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(GroupCooperateApiService.class).getInquisitionRecordByDocId(getView().getQueryMap()), new HttpSubscriber<List<DiagnoseRecoderBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<DiagnoseRecoderBean> data) {
                hideLoading();
                getView().getDiagnoseRecoderSuccess(data, type);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getDiagnoseRecoderFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
