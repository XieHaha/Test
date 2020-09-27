package com.keydom.mianren.ih_patient.activity.child_health.controller;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_patient.activity.child_health.ChildHealthDetailActivity;
import com.keydom.mianren.ih_patient.activity.child_health.view.ChildHealthHistoryView;
import com.keydom.mianren.ih_patient.bean.ChildHealthDoingBean;

/**
 * @author 顿顿
 * @date 20/2/27 11:39
 * @des 儿童保健历史记录
 */
public class ChildHealthHistoryController extends ControllerImpl<ChildHealthHistoryView> implements BaseQuickAdapter.OnItemClickListener {

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ChildHealthDoingBean projectBean = (ChildHealthDoingBean) adapter.getItem(position);
        ChildHealthDetailActivity.start(getContext(), getView().getCardInfo(), projectBean);
    }
}
