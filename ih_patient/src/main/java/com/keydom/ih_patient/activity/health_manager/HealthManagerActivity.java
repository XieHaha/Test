package com.keydom.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.StatusBarUtils;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.health_manager.controller.HealthManagerController;
import com.keydom.ih_patient.activity.health_manager.view.HealthManagerView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @date 20/3/4 10:54
 * @des 健康管理
 */
public class HealthManagerActivity extends BaseControllerActivity<HealthManagerController> implements HealthManagerView {
    @BindView(R.id.iv_online)
    ImageView ivOnline;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_record_date)
    TextView tvRecordDate;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_blood_oxygen)
    TextView tvBloodOxygen;
    @BindView(R.id.tv_blood_glucose)
    TextView tvBloodGlucose;
    @BindView(R.id.tv_blood_pressure)
    TextView tvBloodPressure;
    @BindView(R.id.tv_heart_rate)
    TextView tvHeartRate;
    @BindView(R.id.iv_manager_header)
    ImageView ivManagerHeader;
    @BindView(R.id.tv_manager_name)
    TextView tvManagerName;
    @BindView(R.id.tv_manager_hospital)
    TextView tvManagerHospital;
    @BindView(R.id.tv_manager_title)
    TextView tvManagerTitle;
    @BindView(R.id.layout_abnormal_sign_data)
    LinearLayout layoutAbnormalSignData;

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, HealthManagerActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_manager;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.other_login_color);
        tvTitle.setText(getString(R.string.txt_health_management));
        ivBack.setOnClickListener(getController());
        ivOnline.setOnClickListener(getController());
    }

    @Override
    public void finishPage() {
        finish();
    }
}
