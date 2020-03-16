package com.keydom.mianren.ih_patient.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * 就诊卡二维码页面
 */
public class MedicalCardQrActivity extends BaseActivity {
    private ImageView medicalCardQrImg;
    private TextView medicalCardNumTv;
    private MedicalCardInfo medicalCardInfo;

    /**
     * 启动
     */
    public static void start(Context context,MedicalCardInfo medicalCardInfo) {
        Intent intent=new Intent(context,MedicalCardQrActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("medicalCardInfo",medicalCardInfo);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_medical_card_qr_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.medical_card_qr_title));
        getTitleLayout().initViewsVisible(true, true, false);
        medicalCardQrImg = findViewById(R.id.medical_card_qr_img);
        medicalCardNumTv = findViewById(R.id.medical_card_num_tv);
        medicalCardInfo= (MedicalCardInfo) getIntent().getSerializableExtra("medicalCardInfo");
        medicalCardNumTv.setText("就诊卡号：" + medicalCardInfo.getEleCardNumber());
        if (medicalCardInfo.getEleCardNumber() != null && !"".equals(medicalCardInfo.getEleCardNumber())) {
            //.with(this).load(medicalCardInfo.getQrCode()).into(medicalCardQrImg);
            Bitmap image = CodeUtils.createImage(medicalCardInfo.getEleCardNumber(), 400, 400, null);
            medicalCardQrImg.setImageBitmap(image);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
