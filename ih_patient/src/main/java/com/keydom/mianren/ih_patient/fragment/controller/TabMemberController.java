package com.keydom.mianren.ih_patient.fragment.controller;

import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.member.SignMemberActivity;
import com.keydom.mianren.ih_patient.bean.VIPCardInfoResponse;
import com.keydom.mianren.ih_patient.fragment.view.TabMemberView;
import com.keydom.mianren.ih_patient.net.VIPCardService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TabMemberController extends ControllerImpl<TabMemberView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_tab_member_get_vip_tv:
                SignMemberActivity.start(getContext(),getView().getVipCardInfo());
                break;
            default:
                break;
        }
    }


    /**
     * 获取当前会员卡权益
     */
    public void getMyVipCard() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(VIPCardService.class).getVipCard(), new HttpSubscriber<VIPCardInfoResponse>(getContext(), getDisposable(), false, false) {

            @Override
            public void requestComplete(@Nullable VIPCardInfoResponse data) {
                getView().getMyVipCardSuccess(data);
            }


            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtils.showShort(msg);
                getView().getMyVipCardFail(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
