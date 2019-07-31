package com.keydom.ih_doctor.activity.consulting_arrange.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.activity.consulting_arrange.ArrangeCircleActivity;
import com.keydom.ih_doctor.activity.consulting_arrange.view.ArrangeCircleView;
import com.keydom.ih_doctor.bean.ConsultingBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.net.ScheduingService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：循环排班、停诊共用
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class ArrangeCircleController extends ControllerImpl<ArrangeCircleView> implements OnRefreshListener, OnLoadMoreListener {
    public void getData() {
        if (getView().getType() == ArrangeCircleActivity.CONSULTING_CIRCLE) {
            getLoopList();
        } else {
            getStopConsultingList(TypeEnum.REFRESH);
        }
    }

    private void getStopConsultingList(final TypeEnum type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ScheduingService.class).getStopConsultingList(map), new HttpSubscriber<List<ConsultingBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<ConsultingBean> data) {
                getView().getConsultingSuccess(type, data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getConsultingFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }

    private void getLoopList() {
        HashMap<String, Object> map = new HashMap<>();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ScheduingService.class).getLoopList(map), new HttpSubscriber<List<ConsultingBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<ConsultingBean> data) {
                getView().getConsultingSuccess(TypeEnum.REFRESH, data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getConsultingFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        currentPagePlus();
        getStopConsultingList(TypeEnum.LOAD_MORE);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setDefaultPage();
        getStopConsultingList(TypeEnum.REFRESH);
    }


}
