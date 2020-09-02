package com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.ObstetricHospitalListActivity;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.view.ReserveObstetricHospitalView;

/**
 * @author 顿顿
 * @date 20/3/11 17:00
 * @des
 */
public class ReserveObstetricHospitalController extends ControllerImpl<ReserveObstetricHospitalView> implements View.OnClickListener, IhTitleLayout.OnRightTextClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_next:
                break;
        }
    }

    @Override
    public void OnRightTextClick(View v) {
        ObstetricHospitalListActivity.start(getContext());
    }
}
