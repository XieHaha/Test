package com.keydom.ih_doctor.activity.nurse_service.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.nurse_service.view.ServiceOrderListView;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.constant.EventType;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_tv) {
            EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.SEARCH_NURSE_SERVICE_ORDER).setData(getView().getKeyword()).build());
        }
    }
}
