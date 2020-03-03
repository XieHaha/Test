package com.keydom.ih_patient.activity.medical_mail.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.medical_mail.controller.MedicalMailOneController;
import com.keydom.ih_patient.activity.medical_mail.view.MedicalMailOneView;
import com.keydom.ih_patient.view.MedicalAuthLayout;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * 病案邮寄-身份认证
 */
public class MedicalMailOneFragment extends BaseControllerFragment<MedicalMailOneController> implements MedicalMailOneView {
    @BindView(R.id.tv_select_visit)
    TextView tvSelectVisit;
    @BindView(R.id.layout_front)
    MedicalAuthLayout layoutFront;
    @BindView(R.id.layout_back)
    MedicalAuthLayout layoutBack;
    @BindView(R.id.layout_hand)
    MedicalAuthLayout layoutHand;
    @BindView(R.id.et_name)
    InterceptorEditText etName;
    @BindView(R.id.et_id_card)
    InterceptorEditText etIdCard;
    @BindView(R.id.et_phone)
    InterceptorEditText etPhone;
    @BindView(R.id.tv_next)
    TextView tvNext;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_medical_mail_one;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        tvSelectVisit.setOnClickListener(getController());
        layoutFront.setOnClickListener(getController());
        layoutBack.setOnClickListener(getController());
        layoutHand.setOnClickListener(getController());
        tvNext.setOnClickListener(getController());
    }
}
