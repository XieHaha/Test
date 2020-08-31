package com.keydom.mianren.ih_patient.activity.inspection_report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.inspection_report.controller.ObstetricMedicalController;
import com.keydom.mianren.ih_patient.activity.inspection_report.view.ObstetricMedicalView;
import com.keydom.mianren.ih_patient.adapter.OutpatientNumberAdapter;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/3/7 11:05
 * @des 产科病历首页
 */
public class ObstetricMedicalActivity extends BaseControllerActivity<ObstetricMedicalController> implements ObstetricMedicalView {
    @BindView(R.id.obstetric_medical_head_iv)
    ImageView obstetricMedicalHeadIv;
    @BindView(R.id.obstetric_medical_name)
    TextView obstetricMedicalName;
    @BindView(R.id.obstetric_medical_mark)
    TextView obstetricMedicalMark;
    @BindView(R.id.obstetric_medical_due_date_tv)
    TextView obstetricMedicalDueDateTv;
    @BindView(R.id.obstetric_medical_year)
    TextView obstetricMedicalYear;
    @BindView(R.id.obstetric_medical_month)
    TextView obstetricMedicalMonth;
    @BindView(R.id.obstetric_medical_day)
    TextView obstetricMedicalDay;
    @BindView(R.id.obstetric_medical_recycler_view)
    RecyclerView obstetricMedicalRecyclerView;
    @BindView(R.id.obstetric_medical_date)
    TextView obstetricMedicalDate;
    @BindView(R.id.obstetric_medical_week_tv)
    TextView obstetricMedicalWeekTv;
    @BindView(R.id.obstetric_medical_weight_tv)
    TextView obstetricMedicalWeightTv;
    @BindView(R.id.obstetric_medical_blood_glucose_tv)
    TextView obstetricMedicalBloodGlucoseTv;
    @BindView(R.id.obstetric_medical_circumference_tv)
    TextView obstetricMedicalCircumferenceTv;
    @BindView(R.id.obstetric_medical_fetal_movement_tv)
    TextView obstetricMedicalFetalMovementTv;
    @BindView(R.id.obstetric_medical_hemoglobin_tv)
    TextView obstetricMedicalHemoglobinTv;
    @BindView(R.id.obstetric_medical_height_tv)
    TextView obstetricMedicalHeightTv;
    @BindView(R.id.obstetric_medical_blood_pressure_tv)
    TextView obstetricMedicalBloodPressureTv;
    @BindView(R.id.obstetric_medical_uterus_height_tv)
    TextView obstetricMedicalUterusHeightTv;
    @BindView(R.id.obstetric_medical_fetal_heart_tv)
    TextView obstetricMedicalFetalHeartTv;
    @BindView(R.id.obstetric_medical_double_tv)
    TextView obstetricMedicalDoubleTv;
    @BindView(R.id.obstetric_medical_urine_protein_tv)
    TextView obstetricMedicalUrineProteinTv;
    @BindView(R.id.obstetric_medical_record_tv)
    TextView obstetricMedicalRecordTv;
    @BindView(R.id.obstetric_medical_inspection_report_tv)
    TextView obstetricMedicalInspectionReportTv;
    @BindView(R.id.obstetric_medical_check_report_tv)
    TextView obstetricMedicalCheckReportTv;
    @BindView(R.id.obstetric_medical_prescription_tv)
    TextView obstetricMedicalPrescriptionTv;

    private OutpatientNumberAdapter outpatientNumberAdapter;

    private List<String> outpatientNumbers = new ArrayList<>();

    public static void start(Context context) {
        context.startActivity(new Intent(context, ObstetricMedicalActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_obstetric_medical;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_patient_medical_records));

        outpatientNumbers.add("第一次");
        outpatientNumbers.add("第二次");
        outpatientNumbers.add("第三次");
        outpatientNumbers.add("第四次");
        outpatientNumberAdapter = new OutpatientNumberAdapter(R.layout.item_obstetric_medical_day
                , outpatientNumbers);
        outpatientNumberAdapter.setOnItemClickListener(getController());
        outpatientNumberAdapter.setCurPosition(0);
        obstetricMedicalRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        obstetricMedicalRecyclerView.setAdapter(outpatientNumberAdapter);

        obstetricMedicalRecordTv.setOnClickListener(getController());
        obstetricMedicalInspectionReportTv.setOnClickListener(getController());
        obstetricMedicalCheckReportTv.setOnClickListener(getController());
        obstetricMedicalPrescriptionTv.setOnClickListener(getController());
    }

    @Override
    public void updateOutpatientDate(int position) {
        outpatientNumberAdapter.setCurPosition(position);
        //更新数据
    }
}
