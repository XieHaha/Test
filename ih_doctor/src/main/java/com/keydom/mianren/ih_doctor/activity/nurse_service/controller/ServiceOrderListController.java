package com.keydom.mianren.ih_doctor.activity.nurse_service.controller;

import android.support.annotation.Nullable;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.nurse_service.view.ServiceOrderListView;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.bean.OrderStatisticBean;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.NurseServiceApiService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.fragment.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 下午2:26
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:26
 */
public class ServiceOrderListController extends ControllerImpl<ServiceOrderListView> implements OnRefreshListener, OnLoadMoreListener, View.OnClickListener {


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        currentPagePlus();

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setCurrentPage(1);

    }
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_tv) {
            EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.SEARCH_NURSE_SERVICE_ORDER).setData(getView().getKeyword()).build());
            getOrderStatistic(getView().getStatisticMap());
        }
    }

    public void getOrderStatistic(Map<String,Object> map){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).getOrderStatistic(map), new HttpSubscriber<OrderStatisticBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable OrderStatisticBean data) {
                hideLoading();
                getView().getOrderStatistic(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }
}
