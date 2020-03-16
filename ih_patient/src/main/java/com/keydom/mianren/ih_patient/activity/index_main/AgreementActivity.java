package com.keydom.mianren.ih_patient.activity.index_main;

import android.os.Bundle;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.index_main.Controller.AgreementController;
import com.keydom.mianren.ih_patient.activity.index_main.view.AgreementView;

import org.jetbrains.annotations.Nullable;

public class AgreementActivity extends BaseControllerActivity<AgreementController> implements AgreementView {

    private TextView content_tv;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_agreement_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        content_tv=findViewById(R.id.content_tv);
    }
}
