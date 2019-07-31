package com.keydom.ih_doctor.activity.personal.controller;

import android.app.Activity;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.ApiService;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.personal.view.MyAttentionView;
import com.keydom.ih_doctor.bean.AttentionBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.net.PersonalApiService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：我的关注控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class MyAttentionController extends ControllerImpl<MyAttentionView> implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.attention_search_tv) {
            showLoading();
            setDefaultPage();
            getAttention(getView().getSearchStr(), TypeEnum.REFRESH);
            CommonUtils.hideSoftKeyboard((Activity) getContext());
        }
    }

    /**
     * 获取关注列表
     */
    public void getAttention(String key, final TypeEnum type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("keyword", key);
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).getAttention(map), new HttpSubscriber<ArrayList<AttentionBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable ArrayList<AttentionBean> data) {
                hideLoading();
                getView().getAttentionSuccess(data, type);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 取消关注
     */
    public void cancelAttention(final int position, Map<String, Object> map) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).updateMyAttention(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                if (getView() != null) {
                    hideLoading();
                    getView().cancelSuccess(position, data);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                if (getView() != null) {
                    hideLoading();
                    getView().cancelFailed(msg);
                }
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        getAttention(getView().getSearchStr(), TypeEnum.LOAD_MORE);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setCurrentPage(1);
        getAttention(getView().getSearchStr(), TypeEnum.REFRESH);
    }
}
