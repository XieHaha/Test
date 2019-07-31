package com.keydom.ih_doctor.activity.issue_information.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.activity.issue_information.view.NotificationListView;
import com.keydom.ih_doctor.bean.NotificationBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.net.MainApiService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class NotificationListController extends ControllerImpl<NotificationListView> implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {


    }

    /**
     * 获取通知公告列表
     *
     * @param type 判断是刷新还是加载更多
     */
    public void getNotification(final TypeEnum type) {
        if (type == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        map.put("hospitalId", MyApplication.userInfo.getHospitalId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MainApiService.class).getNotification(map), new HttpSubscriber<List<NotificationBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<NotificationBean> data) {
                getView().getNotificationSuccess(type, data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getNotificationFailed(msg);
                return super.requestError(exception, code, msg);

            }
        });
    }


    /**
     * 删除通知公告
     *
     * @param id       删除的通知公告ID
     * @param position 删除的通知公告在列表中的位置
     */
    public void deleteNotication(int id, final int position) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MainApiService.class).deleteNotice(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().deleteNotificationSuccess(position);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().deleteNotificationFailed(msg);
                return super.requestError(exception, code, msg);

            }
        });
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        currentPagePlus();
        getNotification(TypeEnum.LOAD_MORE);


    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setCurrentPage(1);
        getNotification(TypeEnum.REFRESH);

    }
}
