package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.ChronicDiseaseMainController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.ChronicDiseaseMainView;
import com.keydom.mianren.ih_patient.bean.HealthDataBean;
import com.keydom.mianren.ih_patient.bean.entity.ChronicDisease;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.utils.CommUtil;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 21/3/17 14:27
 * @des 慢病管理
 */
public class ChronicDiseaseMainActivity extends BaseControllerActivity<ChronicDiseaseMainController> implements ChronicDiseaseMainView, ChronicDisease {
    @BindView(R.id.disease_main_bg_iv)
    ImageView diseaseMainBgIv;
    @BindView(R.id.disease_main_data_hint_tv)
    TextView diseaseMainDataHintTv;
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
    @BindView(R.id.disease_main_data_layout)
    LinearLayout diseaseMainDataLayout;
    @BindView(R.id.disease_main_one_data_tv)
    TextView diseaseMainOneDataTv;
    @BindView(R.id.disease_main_two_data_tv)
    TextView diseaseMainTwoDataTv;
    @BindView(R.id.disease_main_three_tv)
    TextView diseaseMainThreeTv;
    @BindView(R.id.disease_main_four_tv)
    TextView diseaseMainFourTv;
    @BindView(R.id.disease_main_one_value_tv)
    TextView diseaseMainOneValueTv;
    @BindView(R.id.disease_main_one_unit_tv)
    TextView diseaseMainOneUnitTv;
    @BindView(R.id.disease_main_one_layout)
    LinearLayout diseaseMainOneLayout;
    @BindView(R.id.disease_main_two_value_tv)
    TextView diseaseMainTwoValueTv;
    @BindView(R.id.disease_main_two_unit_tv)
    TextView diseaseMainTwoUnitTv;
    @BindView(R.id.disease_main_two_layout)
    LinearLayout diseaseMainTwoLayout;
    @BindView(R.id.disease_main_three_value_tv)
    TextView diseaseMainThreeValueTv;
    @BindView(R.id.disease_main_three_unit_tv)
    TextView diseaseMainThreeUnitTv;
    @BindView(R.id.disease_main_three_layout)
    LinearLayout diseaseMainThreeLayout;
    @BindView(R.id.disease_main_four_value_tv)
    TextView diseaseMainFourValueTv;
    @BindView(R.id.disease_main_four_unit_tv)
    TextView diseaseMainFourUnitTv;
    @BindView(R.id.disease_main_four_layout)
    LinearLayout diseaseMainFourLayout;

    /**
     * 健康值
     */
    private HealthDataBean healthDataBean;

    private Calendar calendar;
    /**
     * 当前日期
     */
    private String curSelectDate;
    private String patientId;
    private int chronicDiseaseType = -1;

    /**
     * 启动
     */
    public static void start(Context context, String patientId, int type) {
        Intent intent = new Intent(context, ChronicDiseaseMainActivity.class);
        intent.putExtra(Const.PATIENT_ID, patientId);
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
        patientId = getIntent().getStringExtra(Const.PATIENT_ID);
        bindData();
        //获取当前时间
        calendar = Calendar.getInstance();
        initCalendarView();

        diseaseMainLastDayIv.setOnClickListener(getController());
        diseaseMainNextDayIv.setOnClickListener(getController());
        diseaseMainDayStatusTv.setOnClickListener(getController());
        diseaseMainEatRecordLayout.setOnClickListener(getController());
        diseaseMainSportsRecordLayout.setOnClickListener(getController());
        diseaseMainSleepRecordLayout.setOnClickListener(getController());
        diseaseMainHealthSummaryLayout.setOnClickListener(getController());
        diseaseMainInterventionPlanLayout.setOnClickListener(getController());

    }

    /**
     * 数据初始化
     */
    private void bindData() {
        diseaseMainDataLayout.setVisibility(View.GONE);
        switch (chronicDiseaseType) {
            case CHRONIC_DISEASE_CARDIOVASCULAR:
                setTitle(R.string.txt_cardiovascular_manager);
                diseaseMainBgIv.setImageResource(R.mipmap.icon_cardiovascular_bg);
                diseaseMainOneValueTv.setText("总胆固醇");
                diseaseMainTwoValueTv.setText("甘油三酯");
                diseaseMainThreeValueTv.setText("高密度脂蛋白胆固醇");
                diseaseMainFourValueTv.setText("低密度脂蛋白胆固醇");
                diseaseMainOneUnitTv.setVisibility(View.GONE);
                diseaseMainTwoUnitTv.setVisibility(View.GONE);
                diseaseMainThreeUnitTv.setVisibility(View.GONE);
                diseaseMainFourUnitTv.setVisibility(View.GONE);
                break;
            case CHRONIC_DISEASE_HYPERTENSION:
                setTitle(R.string.txt_hypertension_manager);
                diseaseMainBgIv.setImageResource(R.mipmap.icon_hypertension_bg);
                diseaseMainFourLayout.setVisibility(View.GONE);
                break;
            case CHRONIC_DISEASE_DIABETES:
                setTitle(R.string.txt_diabetes_manager);
                diseaseMainOneValueTv.setText(R.string.txt_blood_glucose);
                diseaseMainOneUnitTv.setText("(mmol/L)");
                diseaseMainBgIv.setImageResource(R.mipmap.icon_diabetes_bg);
                diseaseMainTwoDataTv.setVisibility(View.GONE);
                diseaseMainThreeTv.setVisibility(View.GONE);
                diseaseMainFourTv.setVisibility(View.GONE);
                diseaseMainTwoLayout.setVisibility(View.GONE);
                diseaseMainThreeLayout.setVisibility(View.GONE);
                diseaseMainFourLayout.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    /**
     * 日期处理
     */
    private void initCalendarView() {
        Date date = calendar.getTime();
        curSelectDate = DateUtils.dateToString(date, DateUtils.YYYY_MM_DD_CH);
        diseaseMainDayTv.setText(curSelectDate);
        diseaseMainLastDayIv.setSelected(true);
        if (DateUtils.isToday(date)) {
            diseaseMainNextDayIv.setSelected(false);
        } else {
            diseaseMainNextDayIv.setSelected(true);
        }

        getController().getHeathValue(patientId, curSelectDate);
    }


    @Override
    public int getChronicDiseaseType() {
        return chronicDiseaseType;
    }

    @Override
    public HealthDataBean getHealthDataBean() {
        return healthDataBean;
    }

    @Override
    public void setNewDate(int value) {
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + value);
        initCalendarView();
    }

    @Override
    public Map<String, Object> getUpdateHealthDataParams(HealthDataBean bean) {
        healthDataBean = bean;
        Map<String, Object> params = new HashMap<>();
        params.put("bloodSugar", healthDataBean.getBloodSugar());
        params.put("cholesterol", healthDataBean.getCholesterol());
        params.put("diastolicPressure", healthDataBean.getDiastolicPressure());
        params.put("heartRateValue", healthDataBean.getHeartRateValue());
        params.put("highDensityLipoproteinCholesterol",
                healthDataBean.getHighDensityLipoproteinCholesterol());
        params.put("id", healthDataBean.getId());
        params.put("lowDensityLipoproteinCholesterol",
                healthDataBean.getLowDensityLipoproteinCholesterol());
        params.put("patientId", patientId);
        params.put("systolicPressure", healthDataBean.getSystolicPressure());
        params.put("triglycerides", healthDataBean.getTriglycerides());
        params.put("writeDate", curSelectDate);
        return params;
    }

    @Override
    public void requestHealthDataSuccess(HealthDataBean bean) {
        updateHeathValueSuccess(bean);
    }

    @Override
    public void updateHeathValueSuccess(HealthDataBean bean) {
        healthDataBean = bean;

        switch (chronicDiseaseType) {
            case CHRONIC_DISEASE_CARDIOVASCULAR:
                if (healthDataBean == null || healthDataBean.getCholesterol() == 0) {
                    diseaseMainDataLayout.setVisibility(View.GONE);
                    diseaseMainDataHintTv.setVisibility(View.VISIBLE);
                    diseaseMainDayStatusTv.setText("【填写数据】");
                } else {
                    diseaseMainDayStatusTv.setText("【更新数据】");
                    diseaseMainDataLayout.setVisibility(View.VISIBLE);
                    diseaseMainDataHintTv.setVisibility(View.GONE);
                    diseaseMainOneDataTv.setText(String.valueOf(healthDataBean.getCholesterol()));
                    diseaseMainOneDataTv.setSelected(CommUtil.compareValue(CHOLESTEROL,
                            healthDataBean.getCholesterol()));
                    diseaseMainTwoDataTv.setText(String.valueOf(healthDataBean.getTriglycerides()));
                    diseaseMainTwoDataTv.setSelected(CommUtil.compareValue(TRIGLYCERIDES,
                            healthDataBean.getTriglycerides()));
                    diseaseMainThreeTv.setText(String.valueOf(healthDataBean.getHighDensityLipoproteinCholesterol()));
                    diseaseMainThreeTv.setSelected(CommUtil.compareValue(HIGHDENSITYLIPOPROTEINCHOLESTEROL,
                            healthDataBean.getHighDensityLipoproteinCholesterol()));
                    diseaseMainFourTv.setText(String.valueOf(healthDataBean.getLowDensityLipoproteinCholesterol()));
                    diseaseMainFourTv.setSelected(CommUtil.compareValue(LOWDENSITYLIPOPROTEINCHOLESTEROL,
                            healthDataBean.getLowDensityLipoproteinCholesterol()));
                }
                break;
            case CHRONIC_DISEASE_HYPERTENSION:
                if (healthDataBean == null || healthDataBean.getSystolicPressure() == 0) {
                    diseaseMainDataLayout.setVisibility(View.GONE);
                    diseaseMainDataHintTv.setVisibility(View.VISIBLE);
                    diseaseMainDayStatusTv.setText("【填写数据】");
                } else {
                    diseaseMainDayStatusTv.setText("【更新数据】");
                    diseaseMainDataLayout.setVisibility(View.VISIBLE);
                    diseaseMainDataHintTv.setVisibility(View.GONE);
                    diseaseMainOneDataTv.setText(String.valueOf(healthDataBean.getSystolicPressure()));
                    diseaseMainOneDataTv.setSelected(CommUtil.compareValue(SYSTOLICPRESSURE,
                            healthDataBean.getSystolicPressure()));
                    diseaseMainTwoDataTv.setText(String.valueOf(healthDataBean.getDiastolicPressure()));
                    diseaseMainTwoDataTv.setSelected(CommUtil.compareValue(DIASTOLICPRESSURE,
                            healthDataBean.getDiastolicPressure()));
                    diseaseMainThreeTv.setText(String.valueOf(healthDataBean.getHeartRateValue()));
                    diseaseMainThreeTv.setSelected(CommUtil.compareValue(HEARTRATEVALUE,
                            healthDataBean.getHeartRateValue()));
                }
                break;
            case CHRONIC_DISEASE_DIABETES:
                if (healthDataBean == null || healthDataBean.getBloodSugar() == 0) {
                    diseaseMainDataLayout.setVisibility(View.GONE);
                    diseaseMainDataHintTv.setVisibility(View.VISIBLE);
                    diseaseMainDayStatusTv.setText("【填写数据】");
                } else {
                    diseaseMainDayStatusTv.setText("【更新数据】");
                    diseaseMainDataLayout.setVisibility(View.VISIBLE);
                    diseaseMainDataHintTv.setVisibility(View.GONE);
                    diseaseMainOneDataTv.setText(String.valueOf(healthDataBean.getBloodSugar()));
                    diseaseMainOneDataTv.setSelected(CommUtil.compareValue(BLOODSUGAR,
                            healthDataBean.getBloodSugar()));
                }
                break;
            default:
                break;

        }
    }
}
