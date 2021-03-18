package com.keydom.mianren.ih_patient.activity.chronic_disease;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.chronic_disease.controller.ChronicDiseaseMainController;
import com.keydom.mianren.ih_patient.activity.chronic_disease.view.ChronicDiseaseMainView;
import com.keydom.mianren.ih_patient.constant.Const;

import org.jetbrains.annotations.Nullable;

/**
 * @author 顿顿
 * @date 21/3/17 14:27
 * @des 慢病管理
 */
public class ChronicDiseaseMainActivity extends BaseControllerActivity<ChronicDiseaseMainController> implements ChronicDiseaseMainView {
    /**
     * 心脑血管
     */
    public static final int CHRONIC_DISEASE_CARDIOVASCULAR = 1;
    /**
     * 高血压
     */
    public static final int CHRONIC_DISEASE_HYPERTENSION = 2;
    /**
     * 糖尿病
     */
    public static final int CHRONIC_DISEASE_DIABETES = 3;

    private int chronicDiseaseType = -1;

    /**
     * 启动
     */
    public static void start(Context context, int type) {
        Intent intent = new Intent(context, ChronicDiseaseMainActivity.class);
        intent.putExtra(Const.TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_chronic_disease_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        chronicDiseaseType = getIntent().getIntExtra(Const.TYPE, -1);
        if (chronicDiseaseType == CHRONIC_DISEASE_CARDIOVASCULAR) {
            setTitle(R.string.txt_cardiovascular_manager);
        } else if (chronicDiseaseType == CHRONIC_DISEASE_HYPERTENSION) {
            setTitle(R.string.txt_hypertension_manager);
        } else {
            setTitle(R.string.txt_diabetes_manager);
        }
    }
}
