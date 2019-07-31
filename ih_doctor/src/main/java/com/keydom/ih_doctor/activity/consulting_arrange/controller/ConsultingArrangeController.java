package com.keydom.ih_doctor.activity.consulting_arrange.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.consulting_arrange.ArrangeCircleActivity;
import com.keydom.ih_doctor.activity.consulting_arrange.view.ConsultingArrangeView;
import com.keydom.ih_doctor.bean.ConsultingBean;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.net.ScheduingService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：门诊排班主页
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class ConsultingArrangeController extends ControllerImpl<ConsultingArrangeView> implements View.OnClickListener, OnRefreshListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.consulting_circle_rl:
                ArrangeCircleActivity.startConsultingCircle(getContext());
                break;
            case R.id.consulting_stop_rl:
                ArrangeCircleActivity.startConsultingStop(getContext());
                break;
        }

    }


    /**
     * 获取门诊排班列表
     */
    public void getConsultingList() {
        HashMap<String, Object> map = new HashMap<>();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ScheduingService.class).getConsultingList(map), new HttpSubscriber<List<ConsultingBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<ConsultingBean> data) {
                hideLoading();
                getView().getConsultingSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getConsultingFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        getConsultingList();
    }
}
