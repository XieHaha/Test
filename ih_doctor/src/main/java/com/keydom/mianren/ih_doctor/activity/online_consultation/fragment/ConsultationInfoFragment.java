package com.keydom.mianren.ih_doctor.activity.online_consultation.fragment;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.view.GridViewForScrollView;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.controller.ConsultationInfoController;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationInfoView;
import com.keydom.mianren.ih_doctor.view.DiagnosePrescriptionItemView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @date 20/4/8 14:33
 * @des 会诊室-病历资料
 */
public class ConsultationInfoFragment extends BaseControllerFragment<ConsultationInfoController> implements ConsultationInfoView {
    @BindView(R.id.consultation_info_chief_item)
    DiagnosePrescriptionItemView consultationInfoChiefItem;
    @BindView(R.id.consultation_info_purpose_item)
    DiagnosePrescriptionItemView consultationInfoPurposeItem;
    @BindView(R.id.consultation_info_summary_item)
    DiagnosePrescriptionItemView consultationInfoSummaryItem;
    @BindView(R.id.consultation_info_condition_image_grid)
    GridViewForScrollView consultationInfoConditionImageGrid;
    @BindView(R.id.consultation_info_apply_layout)
    LinearLayout consultationInfoApplyLayout;
    @BindView(R.id.consultation_info_medical_layout)
    LinearLayout consultationInfoMedicalLayout;
    @BindView(R.id.consultation_info_report_layout)
    LinearLayout consultationInfoReportLayout;
    @BindView(R.id.consultation_info_video_layout)
    LinearLayout consultationInfoVideoLayout;

    public static ConsultationInfoFragment newInstance() {
        return new ConsultationInfoFragment();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_consultation_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        consultationInfoApplyLayout.setOnClickListener(getController());
        consultationInfoMedicalLayout.setOnClickListener(getController());
        consultationInfoReportLayout.setOnClickListener(getController());
        consultationInfoVideoLayout.setOnClickListener(getController());
    }

    @Override
    public void onApplyInfoSelect() {
        indexSelected(0);
    }

    @Override
    public void onMedicalSelect() {
        indexSelected(1);
    }

    @Override
    public void onReportInfoSelect() {
        indexSelected(2);
    }

    @Override
    public void onVideoSelect() {
        indexSelected(3);
    }

    private void indexSelected(int index) {
        switch (index) {
            case 0:
                consultationInfoApplyLayout.setSelected(true);
                consultationInfoMedicalLayout.setSelected(false);
                consultationInfoReportLayout.setSelected(false);
                consultationInfoVideoLayout.setSelected(false);
                break;
            case 1:
                consultationInfoApplyLayout.setSelected(false);
                consultationInfoMedicalLayout.setSelected(true);
                consultationInfoReportLayout.setSelected(false);
                consultationInfoVideoLayout.setSelected(false);
                break;
            case 2:
                consultationInfoApplyLayout.setSelected(false);
                consultationInfoMedicalLayout.setSelected(false);
                consultationInfoReportLayout.setSelected(true);
                consultationInfoVideoLayout.setSelected(false);
                break;
            case 3:
                consultationInfoApplyLayout.setSelected(false);
                consultationInfoMedicalLayout.setSelected(false);
                consultationInfoReportLayout.setSelected(false);
                consultationInfoVideoLayout.setSelected(true);
                break;
            default:
                break;
        }
    }
}
