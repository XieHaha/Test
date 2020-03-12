package com.keydom.ih_patient.activity.reserve_amniocentesis.controller;

import android.app.Activity;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisApplyView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;

/**
 * @date 20/3/9 16:09
 * @des 羊水穿刺预约申请
 */
public class AmniocentesisApplyController extends ControllerImpl<AmniocentesisApplyView> implements IhTitleLayout.OnRightTextClickListener, View.OnClickListener {
    @Override
    public void OnRightTextClick(View v) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.amniocentesis_apply_surgery_time_layout:
            case R.id.amniocentesis_apply_last_menstruation_layout:
            case R.id.amniocentesis_apply_due_date_layout:
            case R.id.amniocentesis_apply_birth_layout:
                KeyboardUtils.hideSoftInput((Activity) getContext());
                TimePickerView pickerView = new TimePickerBuilder(getContext(),
                        (date, v1) -> getView().onDateSelect(v, date)).build();
                pickerView.show();
                break;
            case R.id.amniocentesis_apply_get_verify_tv:
                getVerifyCode();
                break;
            case R.id.amniocentesis_apply_next_tv:
                EventBus.getDefault().post(new Event(EventType.AMNIOCENTESIS_APPLY,
                        getView().getReserveBean()));
                break;
            default:
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode() {
    }
}
