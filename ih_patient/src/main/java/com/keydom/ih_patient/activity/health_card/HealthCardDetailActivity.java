package com.keydom.ih_patient.activity.health_card;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.health_card.controller.HealthCardDetailController;
import com.keydom.ih_patient.activity.health_card.view.HealthCardDetailView;
import com.keydom.ih_patient.bean.HealthCardResponse;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HealthCardDetailActivity extends BaseControllerActivity<HealthCardDetailController> implements HealthCardDetailView {


    private TextView mNameTv;
    private TextView mIdTv;
    private ImageView mQrCodeIv;
    private ImageView mQrIconIv;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_card_detail;
    }

    /**
     * 启动方法
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, HealthCardDetailActivity.class));
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("健康卡");
        getTitleLayout().initViewsVisible(true, true, false);
        mNameTv = this.findViewById(R.id.health_card_detail_name_tv);
        mIdTv = this.findViewById(R.id.health_card_detail_card_id_tv);
        mQrCodeIv = this.findViewById(R.id.health_card_detail_qr_code_iv);
        mQrIconIv = this.findViewById(R.id.health_card_detail_qr_icon_iv);

        getController().queryCard();
    }


    @Override
    public void queryCardSuccess(List<HealthCardResponse> datas) {
        if (null != datas && datas.size() > 0) {
            HealthCardResponse data = datas.get(0);
            if (!TextUtils.isEmpty(data.getName())) {
                mNameTv.setText(data.getName());
            }

            if (!TextUtils.isEmpty(data.getEhealthCardId())) {
                mIdTv.setText(data.getEhealthCardId());
                getController().getHealthCardState(data.getEhealthCardId());
            }

        }
    }

    @Override
    public void queryCardFailed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void getQrCodeSuccess(List<HealthCardResponse> datas) {
        if (null != datas && datas.size() > 0) {
            HealthCardResponse data = datas.get(0);
            Bitmap mBitmap = CodeUtils.createImage(data.getEhealthCode(), 400, 400, null);
            mQrCodeIv.setImageBitmap(mBitmap);
            mQrIconIv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getQrCodeFailed(String msg) {
        ToastUtils.showShort(msg);
    }
}

