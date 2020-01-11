package com.keydom.ih_patient.fragment.controller;

import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.member.ChargeMemberActivity;
import com.keydom.ih_patient.activity.member.ChargeMemberRecordActivity;
import com.keydom.ih_patient.bean.VIPDetailBean;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.fragment.view.VIPMemberDetailView;
import com.keydom.ih_patient.net.VIPCardService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VIPMemberDetailController extends ControllerImpl<VIPMemberDetailView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_vip_member_detail_charge_ll:
                ChargeMemberActivity.start(getContext());
                break;
            case R.id.fragment_vip_member_detail_charge_record_ll:
                ChargeMemberRecordActivity.start(getContext());
                break;
            default:
                break;
        }
    }


    /**
     * 获得会员信息
     */
    public void getMyVipCard() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(VIPCardService.class).getMyVipCard(Global.getUserId()), new HttpSubscriber<VIPDetailBean>(getContext(), getDisposable(), true, false) {

            @Override
            public void requestComplete(@Nullable VIPDetailBean data) {
                getView().getMyVipCardSuccess(data);
            }


            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
