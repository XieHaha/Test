package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthArchivesView;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 健康档案
 */
public class HealthArchivesController extends ControllerImpl<HealthArchivesView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.health_archives_base_info_layout:
                break;
            case R.id.health_archives_add_contact_tv:
                break;
            case R.id.health_archives_select_past_tv:
                break;
            case R.id.health_archives_genetic_tv:
                break;
            case R.id.health_archives_add_surgery_tv:
                break;
            case R.id.health_archives_look_more_tv:
                break;
            case R.id.health_archives_drink:
                break;
            case R.id.health_archives_non_drink:
                break;
            case R.id.health_archives_drink_frequency_layout:
                break;
            case R.id.health_archives_drink_quantity_layout:
                break;
            case R.id.health_archives_drink_year_layout:
                break;
            case R.id.health_archives_smoke:
                break;
            case R.id.health_archives_non_smoke:
                break;
            case R.id.health_archives_smoke_frequency_layout:
                break;
            case R.id.health_archives_smoke_quantity_layout:
                break;
            case R.id.health_archives_smoke_year_layout:
                break;
            default:
                break;
        }
    }
}
