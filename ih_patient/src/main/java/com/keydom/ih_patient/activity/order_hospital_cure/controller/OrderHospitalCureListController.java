package com.keydom.ih_patient.activity.order_hospital_cure.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_hospital_cure.view.OrderHospitalCureListView;
import com.keydom.ih_patient.bean.HospitalCureInfo;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.OrderService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 入院列表控制器
 */
public class OrderHospitalCureListController extends ControllerImpl<OrderHospitalCureListView> implements View.OnClickListener, OnRefreshListener {
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.add_order_cure_tv){

        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh();
    }

    /**
     * 查询全部可用就诊卡
     */
    public void queryAdmissionCardList(){
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).getAdmissionCardList(Global.getUserId(), App.hospitalId), new HttpSubscriber<List<HospitalCureInfo>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<HospitalCureInfo> data) {
                hideLoading();
                getView().fillHospitalCureList(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }
}
