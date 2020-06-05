package com.keydom.mianren.ih_doctor.activity.online_diagnose.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.ApplyForCheckActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.CheckOrderDetailActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.view.CheckOrderDetailView;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class CheckOrderDetailController extends ControllerImpl<CheckOrderDetailView> implements View.OnClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit_order_ib) {
            if (getView().getType() == CheckOrderDetailActivity.INSPACT_ORDER) {
                ApplyForCheckActivity.startUpdateInspect(getContext(),
                        getView().getCheckOutOrder(), getView().getInqueryOrder());
            } else {
                ApplyForCheckActivity.startUpdateTest(getContext(),
                        getView().getCheckOutOrder(), getView().getInqueryOrder());
            }
        }
    }
}
