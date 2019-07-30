package com.keydom.ih_patient.fragment.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.upload_certificate_picture.UploadCertificatePictureActivity;
import com.keydom.ih_patient.fragment.view.CardCreateView;

/**
 * 办卡控制器
 */
public class CardCreatController extends ControllerImpl<CardCreateView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send_id_card_tv :
                UploadCertificatePictureActivity.start(getContext(),"card_id_card");
                break;
            case R.id.send_other_certificates_tv :
                UploadCertificatePictureActivity.start(getContext(),"card_other_certificate");
                break;
                default:
                    break;
        }
    }
}
