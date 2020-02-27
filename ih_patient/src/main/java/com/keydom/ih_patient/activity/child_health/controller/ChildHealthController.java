package com.keydom.ih_patient.activity.child_health.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_patient.activity.child_health.view.ChildHealthView;
import com.keydom.ih_patient.constant.TypeEnum;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import map.baidu.ar.utils.ScreenUtils;

/**
 * @date 20/2/27 11:39
 * @des 儿童保健首页控制器
 */
public class ChildHealthController extends ControllerImpl<ChildHealthView> implements OnRefreshListener, OnLoadMoreListener {

    /**
     * 获取儿童保健记录
     */
    public void getChildHealthList(TypeEnum refresh) {

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
}
