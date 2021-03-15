package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthMedicalHistoryController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthMedicalHistoryView;
import com.keydom.mianren.ih_patient.constant.Const;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/3/4 10:54
 * @des 病史
 */
public class HealthMedicalHistoryActivity extends BaseControllerActivity<HealthMedicalHistoryController> implements HealthMedicalHistoryView {
    @BindView(R.id.health_medical_title_tv)
    TextView healthMedicalTitleTv;
    @BindView(R.id.health_medical_hint_tv)
    TextView healthMedicalHintTv;
    @BindView(R.id.health_medical_content_flow_layout)
    TagFlowLayout healthMedicalContentFlowLayout;
    @BindView(R.id.health_medical_other_et)
    InterceptorEditText healthMedicalOtherEt;

    /**
     * 既往病史
     */
    public static final int PAST_MEDICAL_HISTORY = 100;
    /**
     * 遗传史
     */
    public static final int GENETIC_HISTORY = 200;
    private int medicalType;

    /**
     * 启动
     */
    public static void start(Context context, int type) {
        Intent intent = new Intent(context, HealthMedicalHistoryActivity.class);
        intent.putExtra(Const.TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_medical_history;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        medicalType = getIntent().getIntExtra(Const.TYPE, -1);
        if (medicalType == PAST_MEDICAL_HISTORY) {
            setTitle(R.string.txt_past_medical);
            healthMedicalTitleTv.setVisibility(View.GONE);
            healthMedicalHintTv.setText(R.string.txt_past_medical_hint);
        } else {
            setTitle(R.string.txt_genetic);
            healthMedicalTitleTv.setVisibility(View.VISIBLE);
            healthMedicalHintTv.setText(R.string.txt_genetic_hint);
        }
        setRightTxt(getString(R.string.save));
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                finish();
            }
        });
    }
}
