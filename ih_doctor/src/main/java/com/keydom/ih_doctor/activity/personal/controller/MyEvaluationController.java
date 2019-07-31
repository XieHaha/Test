package com.keydom.ih_doctor.activity.personal.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.personal.MyEvaluationActivity;
import com.keydom.ih_doctor.activity.personal.view.MyEvaluationView;
import com.keydom.ih_doctor.bean.EvaluationRes;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.net.PersonalApiService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：我的评价控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class MyEvaluationController extends ControllerImpl<MyEvaluationView> implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.diagnose:
                getView().setDiagnose();
                break;
            case R.id.consult:
                getView().setConsult();
                break;
        }
    }


    /**
     * 获取评价列表
     */
    public void getEvaluation(final TypeEnum type) {
        HashMap<String, Object> map = new HashMap<>();
//        map.put("commentType", getView().getType());
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        if (getView().getReqType() == MyEvaluationActivity.TODAY_EVALUATION || getView().getReqType() == MyEvaluationActivity.TOTAL_EVALUATION) {
            map.put("type", getView().getReqType());
        }
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).patientComment(map), new HttpSubscriber<EvaluationRes>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable EvaluationRes res) {
                hideLoading();
                getView().getEvaluationSuccess(res, type);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        getEvaluation(TypeEnum.LOAD_MORE);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setDefaultPage();
        getEvaluation(TypeEnum.REFRESH);
    }
}
