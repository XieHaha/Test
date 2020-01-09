package com.keydom.ih_patient.fragment.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.member.ChargeMemberRecordActivity;
import com.keydom.ih_patient.activity.member.SignMemberActivity;
import com.keydom.ih_patient.fragment.view.VIPMemberDetailView;

public class VIPMemberDetailController extends ControllerImpl<VIPMemberDetailView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_vip_member_detail_charge_ll:
                SignMemberActivity.start(getContext());
                break;
            case R.id.fragment_vip_member_detail_charge_record_ll:
                ChargeMemberRecordActivity.start(getContext());
                break;
            default:
                break;
        }
    }
}
