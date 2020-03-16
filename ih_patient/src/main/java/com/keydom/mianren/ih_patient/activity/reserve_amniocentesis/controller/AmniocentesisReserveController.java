package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.AmniocentesisRecordActivity;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisReserveView;

/**
 * @date 20/3/2 15:08
 * @des 羊水穿刺预约
 */
public class AmniocentesisReserveController extends ControllerImpl<AmniocentesisReserveView> implements IhTitleLayout.OnRightTextClickListener {
    @Override
    public void OnRightTextClick(View v) {
        AmniocentesisRecordActivity.start(getContext());
    }
}
