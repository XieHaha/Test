package com.keydom.ih_patient.activity.nursing_service;

import android.content.Context;
import android.content.Intent;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.nursing_service.controller.FacialRecognitionController;
import com.keydom.ih_patient.activity.nursing_service.view.FacialRecognitionView;

/**
 * 面部安全识别页面
 */
public class FacialRecognitionActivity extends BaseControllerActivity<FacialRecognitionController> implements FacialRecognitionView {

    /**
     * 启动
     */
    public static void start(Context context){
        context.startActivity(new Intent(context,FacialRecognitionActivity.class));
    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_facial_recognition_layout;
    }
}
