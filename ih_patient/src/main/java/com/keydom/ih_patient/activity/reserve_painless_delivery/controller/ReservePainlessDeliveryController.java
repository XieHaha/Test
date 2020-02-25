package com.keydom.ih_patient.activity.reserve_painless_delivery.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.reserve_painless_delivery.view.ReservePainlessDeliveryView;

/**
 * 无痛分泌预约控制器
 */
public class ReservePainlessDeliveryController extends ControllerImpl<ReservePainlessDeliveryView> implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_visit:
                break;
            case R.id.layout_fetus:
                break;
            case R.id.layout_menstruation:
                break;
            case R.id.layout_due_date:
                break;
            case R.id.tx_next:
                break;
            default:
                break;
        }

    }
}
