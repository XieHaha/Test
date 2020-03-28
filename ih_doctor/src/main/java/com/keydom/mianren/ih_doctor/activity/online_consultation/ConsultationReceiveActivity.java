package com.keydom.mianren.ih_doctor.activity.online_consultation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.im.widget.AutoGridView;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.adapter.ConsultationDoctorAdapter;
import com.keydom.mianren.ih_doctor.activity.online_consultation.controller.ConsultationReceiveController;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationReceiveView;
import com.keydom.mianren.ih_doctor.view.DiagnosePrescriptionItemView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @date 20/3/27 14:38
 * @des 会诊接收
 */
public class ConsultationReceiveActivity extends BaseControllerActivity<ConsultationReceiveController> implements ConsultationReceiveView {
    @BindView(R.id.consultation_receive_header_iv)
    ImageView consultationReceiveHeaderIv;
    @BindView(R.id.consultation_receive_patient_name_tv)
    TextView consultationReceivePatientNameTv;
    @BindView(R.id.consultation_receive_patient_sex_tv)
    TextView consultationReceivePatientSexTv;
    @BindView(R.id.consultation_receive_patient_age_tv)
    TextView consultationReceivePatientAgeTv;
    @BindView(R.id.consultation_receive_level_tv)
    TextView consultationReceiveLevelTv;
    @BindView(R.id.consultation_receive_card_tv)
    TextView consultationReceiveCardTv;
    @BindView(R.id.consultation_receive_visit_time_tv)
    TextView consultationReceiveVisitTimeTv;
    @BindView(R.id.consultation_receive_apply_doctor_header_iv)
    ImageView consultationReceiveApplyDoctorHeaderIv;
    @BindView(R.id.consultation_receive_apply_doctor_name_tv)
    TextView consultationReceiveApplyDoctorNameTv;
    @BindView(R.id.consultation_receive_consultation_doctor_grid_view)
    AutoGridView consultationReceiveConsultationDoctorGridView;
    @BindView(R.id.consultation_receive_consultation_date_tv)
    TextView consultationReceiveConsultationDateTv;
    @BindView(R.id.consultation_receive_consultation_week_tv)
    TextView consultationReceiveConsultationWeekTv;
    @BindView(R.id.consultation_receive_consultation_time_tv)
    TextView consultationReceiveConsultationTimeTv;
    @BindView(R.id.consultation_receive_purpose_item)
    DiagnosePrescriptionItemView consultationReceivePurposeItem;
    @BindView(R.id.consultation_receive_summary_item)
    DiagnosePrescriptionItemView consultationReceiveSummaryItem;
    @BindView(R.id.consultation_receive_information_item)
    DiagnosePrescriptionItemView consultationReceiveInformationItem;
    @BindView(R.id.consultation_receive_advice_item)
    DiagnosePrescriptionItemView consultationReceiveAdviceItem;

    private ConsultationDoctorAdapter doctorAdapter;

    private ArrayList<String> consultationDoctors = new ArrayList<>();

    public static void start(Context context) {
        context.startActivity(new Intent(context, ConsultationReceiveActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_consultation_receive;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_consultation_receive));
        setRightTxt(getString(R.string.txt_receive));
        for (int i = 0; i < 5; i++) {
            consultationDoctors.add("测试数据" + i);
        }
        doctorAdapter = new ConsultationDoctorAdapter(this);
        doctorAdapter.setData(consultationDoctors);
        consultationReceiveConsultationDoctorGridView.setAdapter(doctorAdapter);

        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                ToastUtil.showMessage(ConsultationReceiveActivity.this, "接收");
            }
        });
    }
}
