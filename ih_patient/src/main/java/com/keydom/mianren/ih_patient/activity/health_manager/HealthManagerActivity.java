package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.StatusBarUtils;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthManagerController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthManagerView;

import org.jetbrains.annotations.Nullable;

/**
 * @author 顿顿
 * @date 20/3/4 10:54
 * @des 健康管理
 */
public class HealthManagerActivity extends BaseControllerActivity<HealthManagerController> implements HealthManagerView {
    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, HealthManagerActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_manager_open;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBarUtils.setWindowStatusBarColor(this, R.color.color_f9f9f9);
        setTitle(R.string.txt_health_manager_service);

    }

}
