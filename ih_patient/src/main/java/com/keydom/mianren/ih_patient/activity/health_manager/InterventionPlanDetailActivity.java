package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.JustifiedTextView;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.InterventionPlanDetailController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.InterventionPlanDetailView;
import com.keydom.mianren.ih_patient.bean.InterventionPlanBean;
import com.keydom.mianren.ih_patient.constant.Const;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/3/4 10:54
 * @des 干预方案
 */
public class InterventionPlanDetailActivity extends BaseControllerActivity<InterventionPlanDetailController> implements InterventionPlanDetailView {
    @BindView(R.id.intervention_plan_detail_name_tv)
    TextView interventionPlanDetailNameTv;
    @BindView(R.id.intervention_plan_detail_date_tv)
    TextView interventionPlanDetailDateTv;
    @BindView(R.id.intervention_plan_detail_contact_tv)
    TextView interventionPlanDetailContactTv;
    @BindView(R.id.intervention_plan_detail_eat_tv)
    JustifiedTextView interventionPlanDetailEatTv;
    @BindView(R.id.intervention_plan_detail_sports_tv)
    JustifiedTextView interventionPlanDetailSportsTv;
    @BindView(R.id.intervention_plan_detail_drug_tv)
    JustifiedTextView interventionPlanDetailDrugTv;
    @BindView(R.id.intervention_plan_detail_sleep_tv)
    JustifiedTextView interventionPlanDetailSleepTv;
    private InterventionPlanBean planBean;
    private String planId, patientId, doctorName, userCode;

    /**
     * 启动
     */
    public static void start(Context context, String patientId, String planId) {
        Intent intent = new Intent(context, InterventionPlanDetailActivity.class);
        intent.putExtra(Const.PATIENT_ID, patientId);
        intent.putExtra(Const.RECORD_ID, planId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_intervention_plan_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        patientId = getIntent().getStringExtra(Const.PATIENT_ID);
        planId = getIntent().getStringExtra(Const.RECORD_ID);

        interventionPlanDetailContactTv.setOnClickListener(getController());
        getController().interventionPlanDetail(planId);
    }

    private void bindData() {
        if (planBean != null) {
            setTitle(planBean.getInterventionPlanName());
            doctorName = planBean.getDoctorName();
            userCode = planBean.getUserCode();
            interventionPlanDetailNameTv.setText(doctorName);
            interventionPlanDetailDateTv.setText(planBean.getUpdateTime());
            interventionPlanDetailEatTv.setText(planBean.getFoodPlan());
            interventionPlanDetailSportsTv.setText(planBean.getExercisePlan());
            interventionPlanDetailDrugTv.setText(planBean.getDrugsPlan());
            interventionPlanDetailSleepTv.setText(planBean.getSleepPlan());
        }
    }

    @Override
    public String getPatientId() {
        return patientId;
    }

    @Override
    public String getDoctorName() {
        return doctorName;
    }

    @Override
    public String getUserCode() {
        return userCode;
    }

    @Override
    public void requestInterventionPlanDetailSuccess(InterventionPlanBean planBean) {
        this.planBean = planBean;
        bindData();
    }
}
