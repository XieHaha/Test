package com.keydom.mianren.ih_patient.activity.child_health.controller;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.child_health.ChildHealthDetailActivity;
import com.keydom.mianren.ih_patient.activity.child_health.ChildHealthHistoryActivity;
import com.keydom.mianren.ih_patient.activity.child_health.ChildHealthProjectActivity;
import com.keydom.mianren.ih_patient.activity.child_health.view.ChildHealthView;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.ChoosePatientActivity;
import com.keydom.mianren.ih_patient.bean.ChildHealthDoingBean;
import com.keydom.mianren.ih_patient.bean.ChildHealthRootBean;
import com.keydom.mianren.ih_patient.net.ChildHealthService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import map.baidu.ar.utils.ScreenUtils;

/**
 * @author 顿顿
 * @date 20/2/27 11:39
 * @des 儿童保健首页控制器
 */
public class ChildHealthController extends ControllerImpl<ChildHealthView> implements OnRefreshListener, OnLoadMoreListener, View.OnClickListener, BaseQuickAdapter.OnItemClickListener {

    /**
     * 获取儿童保健记录
     */
    public void getChildHistory() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChildHealthService.class).getChildHistory(getView().getEleCardNumber()), new HttpSubscriber<ChildHealthRootBean>(getContext(), getDisposable(), true, false) {

            @Override
            public void requestComplete(@Nullable ChildHealthRootBean data) {
                getView().requestSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);

            }
        });
    }

    /**
     * 标题栏处理
     */
    public void transTitleBar(int y) {
        //距上最大间距
        float distance = ScreenUtils.dip2px(150, getContext());
        float scale;
        boolean up = distance / 2 > y;
        if (y < distance) {
            scale = y / distance;
        } else {
            scale = 1;
        }
        getView().transTitleBar(up, scale);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {

    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getView().finishPage();
                break;
            case R.id.right_tv:
                ChoosePatientActivity.start(getContext(), -1, false);
                break;
            case R.id.header_child_health_all_project_layout:
                ChildHealthProjectActivity.start(getContext(), getView().getMedicalCardInfo());
                break;
            case R.id.header_child_health_info_layout:
                //查看历史
                ChildHealthHistoryActivity.start(getContext(), getView().getMedicalCardInfo(),
                        getView().getHealthHistoryBeans());
                break;
            case R.id.header_child_health_look_tv:
                //即将进行的项目
                ChildHealthDetailActivity.start(getContext(), getView().getMedicalCardInfo(),
                        getView().getHealthDoingBean());
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ChildHealthDoingBean doingBean = (ChildHealthDoingBean) adapter.getItem(position);
        ChildHealthDetailActivity.start(getContext(), getView().getMedicalCardInfo(), doingBean);
    }
}
