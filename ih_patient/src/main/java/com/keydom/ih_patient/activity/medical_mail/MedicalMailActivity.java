package com.keydom.ih_patient.activity.medical_mail;

import android.content.Context;
import android.content.Intent;

import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.BaseActivity;

/**
 * @date 20/3/2 13:52
 * @des 病案邮寄
 */
public class MedicalMailActivity extends BaseActivity {
    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, MedicalMailActivity.class));
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_medical_mail;
    }
}
