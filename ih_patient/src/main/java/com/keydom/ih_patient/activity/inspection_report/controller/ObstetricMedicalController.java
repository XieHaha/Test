package com.keydom.ih_patient.activity.inspection_report.controller;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.inspection_report.view.ObstetricMedicalView;

/**
 * @date 20/3/7 14:00
 * @des 产科病历首页
 */
public class ObstetricMedicalController extends ControllerImpl<ObstetricMedicalView> implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.obstetric_medical_record_tv:
                break;
            case R.id.obstetric_medical_inspection_report_tv:
                break;
            case R.id.obstetric_medical_check_report_tv:
                break;
            case R.id.obstetric_medical_prescription_tv:
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        getView().updateOutpatientDate(position);
    }
}
