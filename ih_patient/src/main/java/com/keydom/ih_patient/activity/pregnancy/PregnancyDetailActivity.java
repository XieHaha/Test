package com.keydom.ih_patient.activity.pregnancy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.pregnancy.controller.PregnancyDetailController;
import com.keydom.ih_patient.activity.pregnancy.view.PregnancyDetailView;

import org.jetbrains.annotations.Nullable;

public class PregnancyDetailActivity extends BaseControllerActivity<PregnancyDetailController> implements PregnancyDetailView {


    /**
     * 启动
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, PregnancyDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_pregnancy_detail;
    }

    @Override
    public void beforeInit() {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getTitleLayout().initViewsVisible(true, true, false);
        getTitleLayout().setWhiteBar();
        getTitleLayout().setBackgroundColor(getResources().getColor(R.color.vip_pregnancy_detail_tool_bar_bg));
        setTitle("产检预约");

    }



}
