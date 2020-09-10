package com.keydom.mianren.ih_patient.activity.medical_record.controller;

import android.app.Activity;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.medical_record.view.MedicalRecordView;
import com.keydom.mianren.ih_patient.bean.MedicalRecordBean;
import com.keydom.mianren.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * created date: 2019/1/4 on 13:04
 * des:处方记录控制器
 *
 * @author 顿顿
 */
public class MedicalRecordController extends ControllerImpl<MedicalRecordView> implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        Calendar end = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        end.setTime(new Date());
        start.add(Calendar.MONTH, -12);
        switch (v.getId()) {
            case R.id.medical_record_start_date_layout:
                KeyboardUtils.hideSoftInput((Activity) getContext());
                TimePickerView startDate = new TimePickerBuilder(getContext(),
                        (date, v1) -> getView().setStartDate(date)).setRangDate(start, end).build();
                startDate.show();
                break;
            case R.id.medical_record_end_date_layout:
                KeyboardUtils.hideSoftInput((Activity) getContext());
                TimePickerView endDate = new TimePickerBuilder(getContext(),
                        (date, v1) -> getView().setEndDate(date)).setRangDate(start, end).build();
                endDate.show();
                break;
            default:
                break;
        }
    }

    /**
     * 获取门诊病历记录
     */
    public void getIndAllData() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getMedicalRecord(HttpService.INSTANCE.object2Body(getView().getParams())), new HttpSubscriber<List<MedicalRecordBean>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable List<MedicalRecordBean> data) {
                getView().getRecordList(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
