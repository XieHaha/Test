package com.keydom.ih_patient.fragment.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.login.LoginActivity;
import com.keydom.ih_patient.activity.upload_certificate_picture.UploadCertificatePictureActivity;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.fragment.view.CardCreateView;

/**
 * 办卡控制器
 */
public class CardCreatController extends ControllerImpl<CardCreateView> implements View.OnClickListener {



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
                    }).setTitle("提示").setCancel(false).setPositiveButton("登陆").show();
                } else
                    UploadCertificatePictureActivity.start(getContext(), "card_id_card");
                break;
            case R.id.send_other_certificates_tv:
                if (Global.getUserId() == -1) {
                    new GeneralDialog(getContext(), "该功能需要登录才能使用，是否立即登录？", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            LoginActivity.start(getContext());
                        }
                    }).setTitle("提示").setCancel(false).setPositiveButton("登陆").show();
                } else
                    UploadCertificatePictureActivity.start(getContext(), "card_other_certificate");
                break;
            default:
                break;
        }
    }


}
