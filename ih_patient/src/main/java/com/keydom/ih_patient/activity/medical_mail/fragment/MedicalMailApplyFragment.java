package com.keydom.ih_patient.activity.medical_mail.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.medical_mail.controller.MedicalMailApplyController;
import com.keydom.ih_patient.activity.medical_mail.view.MedicalMailApplyView;
import com.keydom.ih_patient.bean.MedicalMailApplyBean;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * 病案邮寄-邮寄申请
 */
public class MedicalMailApplyFragment extends BaseControllerFragment<MedicalMailApplyController> implements MedicalMailApplyView {
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

    private MedicalMailApplyBean applyBean;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_medical_mail_apply;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvNext.setOnClickListener(getController());
        bindData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            bindData();
        }
    }

    private void bindData() {
        etName.setText(applyBean.getPatientName());
        etIdCard.setText(applyBean.getPatientIdCard());
    }

    public void setApplyBean(MedicalMailApplyBean applyBean) {
        this.applyBean = applyBean;
    }

    @Override
    public String getName() {
        return etName.getText().toString();
    }

    @Override
    public String getIdCard() {
        return etIdCard.getText().toString();
    }

    @Override
    public String getLeaveTime() {
        return etLeaveHospitalTime.getText().toString();
    }

    @Override
    public String getWardNumber() {
        return etWardNumber.getText().toString();
    }

    @Override
    public String getInHospitalNumber() {
        return etHospitalNumber.getText().toString();
    }

    @Override
    public String getCopyPurpose() {
        return tvCopyPurpose.getText().toString();
    }

    @Override
    public String getCopyQuantity() {
        return etCopyQuantity.getText().toString();
    }

    @Override
    public String getCopyContent() {
        return tvCopyContent.getText().toString();
    }

    @Override
    public MedicalMailApplyBean getApplyData() {
        return applyBean;
    }
}
