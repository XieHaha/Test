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
import com.keydom.ih_doctor.activity.online_diagnose.view.DrugChooseView;
import com.keydom.ih_doctor.bean.DrugBean;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.net.DiagnoseApiService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：药品选择页面控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class DrugChooseController extends ControllerImpl<DrugChooseView> implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {

    /**
     * 获取药品列表
     * @param type 刷新还是加载更多
     */
    public void drugsList(final TypeEnum type) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).drugsList(getView().getDrugListMap()), new HttpSubscriber<List<DrugBean>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable List<DrugBean> data) {
                getView().getDrugListSuccess(data, type);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getDrugListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_tv) {
            setCurrentPage(1);
            drugsList(TypeEnum.REFRESH);
            CommonUtils.hideSoftKeyboard((Activity) getContext());
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        currentPagePlus();
        drugsList(TypeEnum.LOAD_MORE);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setCurrentPage(1);
        drugsList(TypeEnum.REFRESH);
    }
}
