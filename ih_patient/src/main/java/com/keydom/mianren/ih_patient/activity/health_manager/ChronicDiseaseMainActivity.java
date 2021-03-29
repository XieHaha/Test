package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.ChronicDiseaseMainController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.ChronicDiseaseMainView;
import com.keydom.mianren.ih_patient.bean.entity.ChronicDisease;
import com.keydom.mianren.ih_patient.constant.Const;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 21/3/17 14:27
 * @des 慢病管理
 */
public class ChronicDiseaseMainActivity extends BaseControllerActivity<ChronicDiseaseMainController> implements ChronicDiseaseMainView, ChronicDisease {
    @BindView(R.id.disease_main_data_hint_tv)
    TextView diseaseMainDataHintTv;
    @BindView(R.id.disease_main_systolic_data_tv)
    TextView diseaseMainSystolicDataTv;
    @BindView(R.id.disease_main_diastolic_data_tv)
    TextView diseaseMainDiastolicDataTv;
    @BindView(R.id.disease_main_rate_data_tv)
    TextView diseaseMainRateDataTv;
    @BindView(R.id.disease_main_last_day_iv)
    ImageView diseaseMainLastDayIv;
    @BindView(R.id.disease_main_day_tv)
    TextView diseaseMainDayTv;
    @BindView(R.id.disease_main_day_status_tv)
    TextView diseaseMainDayStatusTv;
    @BindView(R.id.disease_main_next_day_iv)
    ImageView diseaseMainNextDayIv;
    @BindView(R.id.disease_main_eat_record_layout)
    LinearLayout diseaseMainEatRecordLayout;
    @BindView(R.id.disease_main_sleep_record_layout)
    LinearLayout diseaseMainSleepRecordLayout;
    @BindView(R.id.disease_main_sports_record_layout)
    LinearLayout diseaseMainSportsRecordLayout;
    @BindView(R.id.disease_main_health_summary_layout)
    LinearLayout diseaseMainHealthSummaryLayout;
    @BindView(R.id.disease_main_intervention_plan_layout)
    LinearLayout diseaseMainInterventionPlanLayout;
    @BindView(R.id.disease_main_contact_counselor_tv)
    TextView diseaseMainContactCounselorTv;



    private int chronicDiseaseType = -1;

    /**
     * 启动
     */
    public static void start(Context context, int type) {
        Intent intent = new Intent(context, ChronicDiseaseMainActivity.class);
        intent.putExtra(Const.TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_chronic_disease_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        chronicDiseaseType = getIntent().getIntExtra(Const.TYPE, -1);
        if (chronicDiseaseType == CHRONIC_DISEASE_CARDIOVASCULAR) {
            setTitle(R.string.txt_cardiovascular_manager);
        } else if (chronicDiseaseType == CHRONIC_DISEASE_HYPERTENSION) {
            setTitle(R.string.txt_hypertension_manager);
        } else {
            setTitle(R.string.txt_diabetes_manager);
        }

        diseaseMainDataHintTv.setOnClickListener(getController());
        diseaseMainEatRecordLayout.setOnClickListener(getController());
        diseaseMainSportsRecordLayout.setOnClickListener(getController());
        diseaseMainSleepRecordLayout.setOnClickListener(getController());
        diseaseMainHealthSummaryLayout.setOnClickListener(getController());
        diseaseMainInterventionPlanLayout.setOnClickListener(getController());
    }
}
