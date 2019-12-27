package com.keydom.ih_patient.fragment.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.member.ChargeMemberActivity;
import com.keydom.ih_patient.activity.member.SignMemberActivity;
import com.keydom.ih_patient.fragment.view.TabMemberView;

public class TabMemberController extends ControllerImpl<TabMemberView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_tab_member_open_now_tv:
                SignMemberActivity.start(getContext());
                break;
            case R.id.fragment_tab_member_func_charge_record_tv:
                break;
            case R.id.fragment_tab_member_func_charge_tv:
                ChargeMemberActivity.start(getContext());
                break;
            default:
                break;
        }
    }
}
