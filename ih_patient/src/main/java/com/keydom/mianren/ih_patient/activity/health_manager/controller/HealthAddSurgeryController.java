package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.app.Activity;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthAddSurgeryView;

import java.util.Calendar;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 添加手术史
 */
public class HealthAddSurgeryController extends ControllerImpl<HealthAddSurgeryView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_surgery_time_tv:
                Calendar endDate = Calendar.getInstance();
                KeyboardUtils.hideSoftInput((Activity) getContext());
                TimePickerView view = new TimePickerBuilder(getContext(),
                        (date, v1) -> getView().selectSurgeryDate(date)).setRangDate(null,
                        endDate).build();
                view.show();
                break;
            case R.id.add_surgery_status_tv:
                OptionsPickerView pickerView = new OptionsPickerBuilder(getContext(),
                        (options1, option2, options3, v12) -> getView().setSurgeryStatus(options1)).build();
                pickerView.setPicker(getView().getSurgeryStatus());
                pickerView.show();
                break;
            default:
                break;
        }
    }
}
