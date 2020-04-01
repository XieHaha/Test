package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.controller.AmniocentesisEvaluateController;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisEvaluateView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @date 20/3/10 13:34
 * @des 羊水穿刺预约评估
 */
public class AmniocentesisEvaluateFragment extends BaseControllerFragment<AmniocentesisEvaluateController> implements AmniocentesisEvaluateView {
    @BindView(R.id.amniocentesis_evaluate_one_fetus_layout)
    LinearLayout amniocentesisEvaluateOneFetusLayout;
    @BindView(R.id.amniocentesis_evaluate_two_fetus_layout)
    LinearLayout amniocentesisEvaluateTwoFetusLayout;
    @BindView(R.id.amniocentesis_evaluate_more_fetus_layout)
    LinearLayout amniocentesisEvaluateMoreFetusLayout;
    @BindView(R.id.amniocentesis_evaluate_hiv_negative_layout)
    LinearLayout amniocentesisEvaluateHivNegativeLayout;
    @BindView(R.id.amniocentesis_evaluate_hiv_positive_layout)
    LinearLayout amniocentesisEvaluateHivPositiveLayout;
    @BindView(R.id.amniocentesis_evaluate_blood_negative_layout)
    LinearLayout amniocentesisEvaluateBloodNegativeLayout;
    @BindView(R.id.amniocentesis_evaluate_blood_positive_layout)
    LinearLayout amniocentesisEvaluateBloodPositiveLayout;
    @BindView(R.id.amniocentesis_evaluate_ultrasound_yes_layout)
    LinearLayout amniocentesisEvaluateUltrasoundYesLayout;
    @BindView(R.id.amniocentesis_evaluate_ultrasound_no_layout)
    LinearLayout amniocentesisEvaluateUltrasoundNoLayout;
    @BindView(R.id.amniocentesis_evaluate_hypertension_have_layout)
    LinearLayout amniocentesisEvaluateHypertensionHaveLayout;
    @BindView(R.id.amniocentesis_evaluate_hypertension_none_layout)
    LinearLayout amniocentesisEvaluateHypertensionNoneLayout;
    @BindView(R.id.amniocentesis_evaluate_diabetes_have_layout)
    LinearLayout amniocentesisEvaluateDiabetesHaveLayout;
    @BindView(R.id.amniocentesis_evaluate_diabetes_none_layout)
    LinearLayout amniocentesisEvaluateDiabetesNoneLayout;
    @BindView(R.id.amniocentesis_evaluate_next_tv)
    TextView amniocentesisEvaluateNextTv;


    private String isOperateBlood, isOperateHiv;
    private int isOperateFetus = -1, isOperateUltrasound = -1, isOperateHypertension = -1,
            isOperateDiabetes = -1;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_amniocentesis_evaluate;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        amniocentesisEvaluateOneFetusLayout.setOnClickListener(getController());
        amniocentesisEvaluateTwoFetusLayout.setOnClickListener(getController());
        amniocentesisEvaluateMoreFetusLayout.setOnClickListener(getController());
        amniocentesisEvaluateHivNegativeLayout.setOnClickListener(getController());
        amniocentesisEvaluateHivPositiveLayout.setOnClickListener(getController());
        amniocentesisEvaluateBloodNegativeLayout.setOnClickListener(getController());
        amniocentesisEvaluateBloodPositiveLayout.setOnClickListener(getController());
        amniocentesisEvaluateUltrasoundYesLayout.setOnClickListener(getController());
        amniocentesisEvaluateUltrasoundNoLayout.setOnClickListener(getController());
        amniocentesisEvaluateHypertensionHaveLayout.setOnClickListener(getController());
        amniocentesisEvaluateHypertensionNoneLayout.setOnClickListener(getController());
        amniocentesisEvaluateDiabetesHaveLayout.setOnClickListener(getController());
        amniocentesisEvaluateDiabetesNoneLayout.setOnClickListener(getController());
        amniocentesisEvaluateNextTv.setOnClickListener(getController());
    }

    @Override
    public void onFetusSelect(int index) {
        isOperateFetus = index;
        switch (index) {
            case 1:
                amniocentesisEvaluateOneFetusLayout.setSelected(true);
                amniocentesisEvaluateTwoFetusLayout.setSelected(false);
                amniocentesisEvaluateMoreFetusLayout.setSelected(false);
                break;
            case 2:
                amniocentesisEvaluateOneFetusLayout.setSelected(false);
                amniocentesisEvaluateTwoFetusLayout.setSelected(true);
                amniocentesisEvaluateMoreFetusLayout.setSelected(false);
                break;
            case 3:
                amniocentesisEvaluateOneFetusLayout.setSelected(false);
                amniocentesisEvaluateTwoFetusLayout.setSelected(false);
                amniocentesisEvaluateMoreFetusLayout.setSelected(true);
                break;
        }
    }

    @Override
    public void onHivSelect(int index) {
        if (index == 0) {
            isOperateHiv = getString(R.string.txt_negative);
            amniocentesisEvaluateHivNegativeLayout.setSelected(true);
            amniocentesisEvaluateHivPositiveLayout.setSelected(false);
        } else {
            isOperateHiv = getString(R.string.txt_positive);
            amniocentesisEvaluateHivNegativeLayout.setSelected(false);
            amniocentesisEvaluateHivPositiveLayout.setSelected(true);
        }
    }

    @Override
    public void onBloodSelect(int index) {
        if (index == 0) {
            isOperateBlood = getString(R.string.txt_negative);
            amniocentesisEvaluateBloodNegativeLayout.setSelected(true);
            amniocentesisEvaluateBloodPositiveLayout.setSelected(false);
        } else {
            isOperateBlood = getString(R.string.txt_positive);
            amniocentesisEvaluateBloodNegativeLayout.setSelected(false);
            amniocentesisEvaluateBloodPositiveLayout.setSelected(true);
        }
    }

    @Override
    public void onUltrasoundSelect(int index) {
        if (index == 0) {
            isOperateUltrasound = 1;
            amniocentesisEvaluateUltrasoundYesLayout.setSelected(true);
            amniocentesisEvaluateUltrasoundNoLayout.setSelected(false);
        } else {
            isOperateUltrasound = 0;
            amniocentesisEvaluateUltrasoundYesLayout.setSelected(false);
            amniocentesisEvaluateUltrasoundNoLayout.setSelected(true);
        }
    }

    @Override
    public void onHypertensionSelect(int index) {
        if (index == 0) {
            isOperateHypertension = 1;
            amniocentesisEvaluateHypertensionHaveLayout.setSelected(true);
            amniocentesisEvaluateHypertensionNoneLayout.setSelected(false);
        } else {
            isOperateHypertension = 0;
            amniocentesisEvaluateHypertensionHaveLayout.setSelected(false);
            amniocentesisEvaluateHypertensionNoneLayout.setSelected(true);
        }
    }

    @Override
    public void onDiabetesSelect(int index) {
        if (index == 0) {
            isOperateDiabetes = 1;
            amniocentesisEvaluateDiabetesHaveLayout.setSelected(true);
            amniocentesisEvaluateDiabetesNoneLayout.setSelected(false);
        } else {
            isOperateDiabetes = 0;
            amniocentesisEvaluateDiabetesHaveLayout.setSelected(false);
            amniocentesisEvaluateDiabetesNoneLayout.setSelected(true);
        }
    }

    @Override
    public int getFetusSelect() {
        return isOperateFetus;
    }

    @Override
    public String getHivSelect() {
        return isOperateHiv;
    }

    @Override
    public String getBloodSelect() {
        return isOperateBlood;
    }

    @Override
    public int getUltrasoundSelect() {
        return isOperateUltrasound;
    }

    @Override
    public int getHypertensionSelect() {
        return isOperateHypertension;
    }

    @Override
    public int getDiabetesSelect() {
        return isOperateDiabetes;
    }

    @Override
    public void onAmniocentesisEvaluateSuccess() {
        //        ToastUtil.showMessage(getContext(), "提交成功");
        //        EventBus.getDefault().post(new Event(EventType.AMNIOCENTESIS_WEB_AGREE, null));
    }

    @Override
    public boolean isSelect() {
        return isOperateFetus != -1
                && !TextUtils.isEmpty(isOperateHiv)
                && !TextUtils.isEmpty(isOperateBlood)
                && isOperateUltrasound != -1
                && isOperateHypertension != -1
                && isOperateDiabetes != -1;
    }
}
