package com.keydom.ih_patient.activity.medical_mail.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.medical_mail.controller.MedicalMailEndController;
import com.keydom.ih_patient.activity.medical_mail.view.MedicalMailEndView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * 病案邮寄-订单确认
 */
public class MedicalMailEndFragment extends BaseControllerFragment<MedicalMailEndController> implements MedicalMailEndView {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_patient_name)
    TextView tvPatientName;
    @BindView(R.id.tv_in_hospital_time)
    TextView tvInHospitalTime;
    @BindView(R.id.tv_copy_quantity)
    TextView tvCopyQuantity;
    @BindView(R.id.tv_recv_type)
    TextView tvRecvType;
    @BindView(R.id.tv_copy_purpose)
    TextView tvCopyPurpose;
    @BindView(R.id.tv_copy_content)
    TextView tvCopyContent;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_pay)
    TextView tvPay;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_medical_mail_end;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvPay.setOnClickListener(getController());
    }
}
