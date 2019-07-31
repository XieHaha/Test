package com.keydom.ih_patient.fragment;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.fragment.controller.CardCreatController;
import com.keydom.ih_patient.fragment.view.CardCreateView;

import org.jetbrains.annotations.NotNull;

/**
 * 办卡页面
 */
public class CardCreateFragment extends BaseControllerFragment<CardCreatController> implements CardCreateView {
   private TextView send_id_card_tv,send_other_certificates_tv;
    private boolean hasGotToken = false;
    private AlertDialog.Builder alertDialog;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_create_card;
    }

    @Override
    public void onViewCreated(@NotNull View view, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alertDialog = new AlertDialog.Builder(getContext());
        send_id_card_tv=view.findViewById(R.id.send_id_card_tv);
        send_id_card_tv.setOnClickListener(getController());
        send_other_certificates_tv=view.findViewById(R.id.send_other_certificates_tv);
        send_other_certificates_tv.setOnClickListener(getController());
        initAccessToken();
    }

    /**
     * 以license文件方式初始化
     */
    private void initAccessToken() {
        OCR.getInstance(getContext()).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("licence方式获取token失败", error.getMessage());
            }
        }, getContext());
    }

    private void alertText(final String title, final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }
}
