package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthAddSurgeryController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthAddSurgeryView;

import org.jetbrains.annotations.Nullable;

/**
 * @author 顿顿
 * @date 20/3/4 10:54
 * @des 添加手术史
 */
public class HealthAddSurgeryActivity extends BaseControllerActivity<HealthAddSurgeryController> implements HealthAddSurgeryView {

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, HealthAddSurgeryActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_surgery_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.txt_add_relatives);
    }
}
