package com.keydom.ih_patient.activity.pregnancy.controller;

import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.pregnancy.ChooseInspectItemActivity;
import com.keydom.ih_patient.activity.pregnancy.view.PregnancyDetailView;
import com.keydom.ih_patient.bean.CheckProjectsItem;
import com.keydom.ih_patient.bean.PregnancyOrderTime;
import com.keydom.ih_patient.net.PregnancyService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PregnancyDetailController extends ControllerImpl<PregnancyDetailView> implements View.OnClickListener {


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pregnancy_order_date_root_rl:
                showDateDialog(getView().getSelectedDate());
                break;
            case R.id.pregnancy_check_projects_root_rl:
                ChooseInspectItemActivity.start(getContext(), getView().getCheckProjects(), null);
                break;
            case R.id.pregnancy_detail_order_check_root_ll:
                getView().setChecks(!getView().isOrderChecks());
                break;
            case R.id.pregnancy_detail_order_diagnose_root_ll:
                getView().setOrderDiagnose(!getView().isOrderDiagnose());
                break;
        }
    }


    private void showDateDialog(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            if (!TextUtils.isEmpty(dateStr)) {
                date = formatter.parse(dateStr);
            } else
                date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    getView().setOrderDate(date);
                }
            }).setDate(calendar).build();
            pvTime.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取宝妈关怀信息
     */
    public void getCheckProjects() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PregnancyService.class).getCheckProjects(), new HttpSubscriber<List<CheckProjectsItem>>(getContext(), getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable List<CheckProjectsItem> data) {
                if (data != null) {
                    getView().getCheckProjectsSuccess(data);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getCheckProjectsFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 获取宝妈关怀信息
     */
    public void getCheckProjectsTimes(String projectId) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PregnancyService.class).getCheckProjectsTimes(projectId), new HttpSubscriber<List<PregnancyOrderTime>>(getContext(), getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable List<PregnancyOrderTime> data) {
                if (data != null) {
                    getView().getCheckProjectsTimesSuccess(data);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getCheckProjectsTimesFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
