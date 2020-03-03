package com.keydom.ih_patient.activity.medical_mail.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.medical_mail.controller.MedicalMailTwoController;
import com.keydom.ih_patient.activity.medical_mail.view.MedicalMailTwoView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * 病案邮寄-邮寄申请
 */
public class MedicalMailTwoFragment extends BaseControllerFragment<MedicalMailTwoController> implements MedicalMailTwoView {
    @BindView(R.id.et_name)
    InterceptorEditText etName;
    @BindView(R.id.et_id_card)
    InterceptorEditText etIdCard;
    @BindView(R.id.et_leave_hospital_time)
    InterceptorEditText etLeaveHospitalTime;
    @BindView(R.id.et_ward_number)
    InterceptorEditText etWardNumber;
    @BindView(R.id.et_hospital_number)
    InterceptorEditText etHospitalNumber;
    @BindView(R.id.tv_copy_purpose)
    TextView tvCopyPurpose;
    @BindView(R.id.et_copy_quantity)
    InterceptorEditText etCopyQuantity;
    @BindView(R.id.tv_copy_content)
    TextView tvCopyContent;
    @BindView(R.id.tv_next)
    TextView tvNext;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_medical_mail_two;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvNext.setOnClickListener(getController());
    }
}
