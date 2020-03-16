package com.keydom.mianren.ih_patient.fragment.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.login.LoginActivity;
import com.keydom.mianren.ih_patient.activity.new_card.NewCardActivity;
import com.keydom.mianren.ih_patient.activity.upload_certificate_picture.UploadCertificatePictureActivity;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.fragment.view.CardCreateView;
import com.keydom.mianren.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 办卡控制器
 */
public class CardCreatController extends ControllerImpl<CardCreateView> implements View.OnClickListener {


    boolean isBindMyself = true;

    public boolean isBindMyself() {
        return isBindMyself;
    }

    public void setBindMyself(boolean bindMyself) {
        isBindMyself = bindMyself;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_id_card_tv:
                if (Global.getUserId() == -1) {
                    new GeneralDialog(getContext(), "该功能需要登录才能使用，是否立即登录？", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            LoginActivity.start(getContext());
                        }
                    }).setTitle("提示").setCancel(false).setPositiveButton("登录").show();
                } else{
                    if(App.userInfo.isCertification() && isBindMyself){
                        NewCardActivity.start(getContext(), Const.CARD_GET_INFO);
                    }else{
                        UploadCertificatePictureActivity.start(getContext(), "card_id_card");
                    }
                }

                break;
            case R.id.send_other_certificates_tv:
                if (Global.getUserId() == -1) {
                    new GeneralDialog(getContext(), "该功能需要登录才能使用，是否立即登录？", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            LoginActivity.start(getContext());
                        }
                    }).setTitle("提示").setCancel(false).setPositiveButton("登录").show();
                } else
                    UploadCertificatePictureActivity.start(getContext(), "card_other_certificate");
                break;
            default:
                break;
        }
    }



    /**
     * 是否已经办卡
     */
    public void isApplyElectronicCard() {
        showLoading();
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("userId",  Global.getUserId());
        map.put("hospitalId", App.hospitalId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).isApplyElectronicCard(map), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().isApplyElectronicCardSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().isApplyElectronicCardFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
