package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthContactView;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 紧急联系人
 */
public class HealthContactController extends ControllerImpl<HealthContactView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.health_contact_relationship_tv:
                OptionsPickerView pickerView = new OptionsPickerBuilder(getContext(),
                        (options1, option2, options3, v12) -> getView().setRelationship(options1)).build();
                pickerView.setPicker(getView().getRelationshipData());
                pickerView.show();
                break;
            default:
                break;
        }
    }
}
