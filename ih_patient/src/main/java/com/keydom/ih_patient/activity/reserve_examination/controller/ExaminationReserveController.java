package com.keydom.ih_patient.activity.reserve_examination.controller;

import android.app.Activity;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.diagnose_user_manager.ManageUserNewActivity;
import com.keydom.ih_patient.activity.reserve_examination.view.ExaminationReserveView;

/**
 * @date 20/3/6 10:55
 * @des
 */
public class ExaminationReserveController extends ControllerImpl<ExaminationReserveView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.examination_reserve_visit_tv:
                ManageUserNewActivity.start(getContext(), getView().getCurrentUserId(),
                        ManageUserNewActivity.FROM_SELECT);

                break;
            case R.id.examination_reserve_date_tv:
                KeyboardUtils.hideSoftInput((Activity) getContext());
                TimePickerView pvTime = new TimePickerBuilder(getContext(),
                        (date, v1) -> getView().setVisitDate(date)).build();
                pvTime.show();
                break;
            case R.id.examination_reserve_checkout_tv:
                getView().setCategory(true);
                break;
            case R.id.examination_reserve_examine_tv:
                getView().setCategory(false);
                break;
            case R.id.examination_reserve_project_tv:
                break;
            case R.id.examination_reserve_notice_layout:
                getView().setSelect();
                break;
            case R.id.examination_reserve_next_tv:
                onNext();
                break;
            default:
                break;
        }
    }

    private void onNext() {

        examinationReserve();
    }

    /**
     * 检验检查预约提交
     */
    private void examinationReserve() {

    }
}
