package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.app.Activity;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthArchivesBaseView;
import com.keydom.mianren.ih_patient.utils.NoFastClickUtils;
import com.keydom.mianren.ih_patient.utils.SelectDialogUtils;

import java.util.Calendar;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 健康档案-基本信息
 */
public class HealthArchivesBaseController extends ControllerImpl<HealthArchivesBaseView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        OptionsPickerView pickerView;
        switch (v.getId()) {
            case R.id.archives_base_male_tv:
                getView().setSexSelect(0);
                break;
            case R.id.archives_base_female_tv:
                getView().setSexSelect(1);
                break;
            case R.id.archives_base_birth_tv:
                Calendar endDate = Calendar.getInstance();
                KeyboardUtils.hideSoftInput((Activity) getContext());
                TimePickerView view = new TimePickerBuilder(getContext(),
                        (date, v1) -> getView().onBirthDaySelect(date)).setRangDate(null,
                        endDate).build();
                view.show();
                break;
            case R.id.archives_base_job_tv:
                pickerView = new OptionsPickerBuilder(getContext(),
                        (options1, option2, options3, v12) -> getView().setJobType(options1)).build();
                pickerView.setPicker(getView().getJobTypes());
                pickerView.show();
                break;
            case R.id.archives_base_nation_tv:
                if (NoFastClickUtils.isFastClick()) {
                    SelectDialogUtils.showNationSelectDialog(getContext(), getView().getNation(),
                            nationBean -> getView().setNation(nationBean.getNationName()));
                } else {
                    ToastUtil.showMessage(getContext(), "请勿频繁点击");
                }
                break;
            case R.id.archives_base_marital_tv:
                getView().setMarrySelect(0);
                break;
            case R.id.archives_base_unmarried_tv:
                getView().setMarrySelect(1);
                break;
            case R.id.archives_base_birth_status_tv:
                pickerView = new OptionsPickerBuilder(getContext(),
                        (options1, option2, options3, v12) -> getView().setBirthStatus(options1)).build();
                pickerView.setPicker(getView().getBirthStatus());
                pickerView.show();
                break;
            default:
                break;
        }
    }
}
