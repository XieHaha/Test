package com.keydom.mianren.ih_patient.activity.card_operate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.card_operate.controller.ElectronicCardDetailController;
import com.keydom.mianren.ih_patient.activity.card_operate.view.ElectronicCardDetailView;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.Const;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;


/**
 * @author 顿顿
 * @date 20/9/22 15:17
 * @des 电子健康卡
 */
public class ElectronicCardDetailActivity extends BaseControllerActivity<ElectronicCardDetailController> implements ElectronicCardDetailView {
    @BindView(R.id.card_detail_name_tv)
    TextView cardDetailNameTv;
    @BindView(R.id.card_detail_qr_iv)
    ImageView cardDetailQrIv;
    private MedicalCardInfo cardInfo;

    /**
     * 启动方法
     */
    public static void start(Context context, MedicalCardInfo info) {
        Intent intent = new Intent(context, ElectronicCardDetailActivity.class);
        intent.putExtra(Const.DATA, info);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_electronic_card_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("电子健康卡");

        cardInfo = (MedicalCardInfo) getIntent().getSerializableExtra(Const.DATA);
        getController().queryHealthCardDetail();

        if (cardInfo != null) {
            cardDetailNameTv.setText(cardInfo.getName());
        }
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> map = new HashMap<>();
        map.put("eleCardNumber", cardInfo.getEleCardNumber());
        map.put("idCard", cardInfo.getIdCard());
        return map;
    }

    @Override
    public void queryHealthCardSuccess(String data) {
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_qr_logo);
        Bitmap mBitmap = CodeUtils.createImage(data, 400, 400, logo);
        cardDetailQrIv.setImageBitmap(mBitmap);
    }

    @Override
    public void queryHealthCardFailed(String msg) {

    }
}
