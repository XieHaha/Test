package com.keydom.ih_patient.activity.pregnancy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.pregnancy.controller.PregnancyController;
import com.keydom.ih_patient.activity.pregnancy.view.PregnancyView;

import org.jetbrains.annotations.Nullable;

public class PregnancyActivity extends BaseControllerActivity<PregnancyController> implements PregnancyView {



    /**
     * 启动
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, PregnancyActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_pregnancy;
    }

    @Override
    public void beforeInit() {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
