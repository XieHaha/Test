package com.keydom.ih_doctor.activity.online_diagnose.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.activity.online_diagnose.view.DiagnoseOrderSelectView;
import com.keydom.ih_doctor.bean.InquiryBean;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.net.GroupCooperateApiService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class DiagnoseOrderSelectController extends ControllerImpl<DiagnoseOrderSelectView> implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {
    @Override
    public void onClick(View v) {


    }


    /**
     * 获取问诊单
     *
     * @param type
     */
    public void getOrder(final TypeEnum type) {
        HashMap<String, Object> map = new HashMap<>();
//        map.put("currentPage", getCurrentPage());
//        map.put("pageSize", Const.PAGE_SIZE);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(GroupCooperateApiService.class).listDoctorInquisition(map), new HttpSubscriber<List<InquiryBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<InquiryBean> data) {
                hideLoading();
                getView().getOrderSuccess(type, data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getOrderFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        currentPagePlus();
        getOrder(TypeEnum.LOAD_MORE);


    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setCurrentPage(1);
        getOrder(TypeEnum.REFRESH);

    }
}
