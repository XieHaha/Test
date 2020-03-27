package com.keydom.mianren.ih_doctor.activity.online_consultation;

import android.content.Context;
import android.content.Intent;

import com.keydom.ih_common.base.BaseActivity;
import com.keydom.mianren.ih_doctor.R;

/**
 * @date 20/3/27 14:38
 * @des 会诊接收
 */
public class ConsultationReceiveActivity extends BaseActivity {
    public static void start(Context context) {
        context.startActivity(new Intent(context, ConsultationReceiveActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_consultation_receive;
    }
}
