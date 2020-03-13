package com.keydom.ih_doctor.activity.health_manager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.health_manager.controller.PatientInfoController;
import com.keydom.ih_doctor.activity.health_manager.view.PatientInfoView;
import com.keydom.ih_doctor.view.SlipButton;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @date 20/3/13 9:13
 * @des 患者信息
 */
public class PatientInfoActivity extends BaseControllerActivity<PatientInfoController> implements PatientInfoView {
    @BindView(R.id.patient_info_head_iv)
    ImageView patientInfoHeadIv;
    @BindView(R.id.patient_info_name_tv)
    TextView patientInfoNameTv;
    @BindView(R.id.patient_info_sex_tv)
    TextView patientInfoSexTv;
    @BindView(R.id.patient_info_age_tv)
    TextView patientInfoAgeTv;
    @BindView(R.id.patient_info_phone_tv)
    TextView patientInfoPhoneTv;
    @BindView(R.id.patient_info_address_tv)
    TextView patientInfoAddressTv;
    @BindView(R.id.patient_info_slip_bt)
    SlipButton patientInfoSlipBt;
    @BindView(R.id.patient_info_remark_et)
    InterceptorEditText patientInfoRemarkEt;
    @BindView(R.id.patient_info_oxygen_tv)
    TextView patientInfoOxygenTv;
    @BindView(R.id.patient_info_pressure_tv)
    TextView patientInfoPressureTv;
    @BindView(R.id.patient_info_glucose_tv)
    TextView patientInfoGlucoseTv;
    @BindView(R.id.patient_info_heart_rate_tv)
    TextView patientInfoHeartRateTv;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_patient_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_patient_info));
    }
}
