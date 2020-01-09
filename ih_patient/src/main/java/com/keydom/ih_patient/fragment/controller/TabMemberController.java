package com.keydom.ih_patient.fragment.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.member.SignMemberActivity;
import com.keydom.ih_patient.fragment.view.TabMemberView;

public class TabMemberController extends ControllerImpl<TabMemberView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_tab_member_get_vip_tv:
                SignMemberActivity.start(getContext());
                break;
            default:
                break;
        }
    }
}
