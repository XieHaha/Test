package com.keydom.mianren.ih_patient.fragment.controller;

import android.app.Activity;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.InspectionRecordBean;
import com.keydom.mianren.ih_patient.fragment.view.InspectionReportFmView;
import com.keydom.mianren.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 检验检查报告控制器
 *
 * @author 顿顿
 */
public class InspectionReportFmController extends ControllerImpl<InspectionReportFmView> implements View.OnClickListener {
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
     * 获取检验报告
     */
    public void getInspectionReportList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getCheckoutRecordPage(HttpService.INSTANCE.object2Body(getView().getParams())), new HttpSubscriber<List<InspectionRecordBean>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable List<InspectionRecordBean> data) {
                getView().getDataListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getDataListFailed(code, msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
