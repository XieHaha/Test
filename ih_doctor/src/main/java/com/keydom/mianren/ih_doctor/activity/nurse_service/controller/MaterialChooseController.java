package com.keydom.mianren.ih_doctor.activity.nurse_service.controller;

import android.app.Activity;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.nurse_service.view.MaterialChooseView;
import com.keydom.mianren.ih_doctor.bean.MaterialBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.net.DiagnoseApiService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class MaterialChooseController extends ControllerImpl<MaterialChooseView> implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {

    /**
     * 获取耗材列表
     * @param type 判断刷新还是加载更多
     */
    public void materialList(final TypeEnum type) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getAllConsumableItems(getView().getMaterialListMap()), new HttpSubscriber<List<MaterialBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<MaterialBean> data) {
                hideLoading();
                getView().getMaterialListSuccess(data, type);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getMaterialListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_tv) {
            showLoading();
            setCurrentPage(1);
            materialList(TypeEnum.REFRESH);
            CommonUtils.hideSoftKeyboard((Activity) getContext());
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        currentPagePlus();
        materialList(TypeEnum.LOAD_MORE);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setCurrentPage(1);
        materialList(TypeEnum.REFRESH);
    }
}
