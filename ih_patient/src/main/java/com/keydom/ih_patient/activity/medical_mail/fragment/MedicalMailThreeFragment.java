package com.keydom.ih_patient.activity.medical_mail.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.medical_mail.controller.MedicalMailThreeController;
import com.keydom.ih_patient.activity.medical_mail.view.MedicalMailThreeView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * 病案邮寄-收件信息
 */
public class MedicalMailThreeFragment extends BaseControllerFragment<MedicalMailThreeController> implements MedicalMailThreeView {
    @BindView(R.id.tv_select_address)
    TextView tvSelectAddress;
    @BindView(R.id.et_name)
    InterceptorEditText etName;
    @BindView(R.id.et_phone)
    InterceptorEditText etPhone;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.et_address_detail)
    InterceptorEditText etAddressDetail;
    @BindView(R.id.tv_next)
    TextView tvNext;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_medical_mail_three;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvNext.setOnClickListener(getController());
    }

}
