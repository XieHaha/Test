package com.keydom.ih_doctor.activity.online_diagnose.controller;

import android.app.Activity;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.online_diagnose.view.DiagnoseInputView;
import com.keydom.ih_doctor.bean.ICD10Bean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.net.DiagnoseApiService;
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
public class DiagnoseInputController extends ControllerImpl<DiagnoseInputView> implements OnRefreshListener, OnLoadMoreListener, View.OnClickListener {


    /**
     * 获取ICD－10数据
     */
    public void icdCateList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).idcCateList(getView().getKeyWord(), getCurrentPage(), Const.PAGE_SIZE), new HttpSubscriber<List<ICD10Bean>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable List<ICD10Bean> data) {
                getView().getICDListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getICDListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        icdCateList();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        getView().clearList();
        setCurrentPage(1);
        icdCateList();
    }
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_tv) {
            CommonUtils.hideSoftKeyboard((Activity) getContext());
            getView().clearList();
            setCurrentPage(1);
            icdCateList();
        }
    }
}
