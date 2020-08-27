package com.keydom.mianren.ih_patient.activity.pregnancy.controller;

import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.pregnancy.ChooseInspectItemActivity;
import com.keydom.mianren.ih_patient.activity.pregnancy.view.PregnancyReserveView;
import com.keydom.mianren.ih_patient.bean.CheckProjectRootBean;
import com.keydom.mianren.ih_patient.bean.PregnancyOrderTime;
import com.keydom.mianren.ih_patient.net.PregnancyService;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PregnancyReserveController extends ControllerImpl<PregnancyReserveView> implements View.OnClickListener {


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pregnancy_order_date_root_rl:
                showDateDialog(getView().getSelectedDate());
                break;
            case R.id.pregnancy_check_projects_root_rl:
                ChooseInspectItemActivity.start(getContext(), getView().getCheckProjects(),
                        getView().getSelectSubBeans());
                break;
            case R.id.pregnancy_detail_order_check_root_ll:
                getView().setChecks(!getView().isOrderChecks());
                break;
            case R.id.pregnancy_detail_order_diagnose_root_ll:
                getView().setOrderDiagnose(!getView().isOrderDiagnose());
                break;
            case R.id.pregnancy_detail_order_tv:
                if (TextUtils.isEmpty(getView().getRecordID())) {
                    ToastUtils.showShort("请确认是否存在该预约");
                    return;
                }

                if (getView().getAppointType() <= 0) {
                    ToastUtils.showShort("请选择预约事项");
                    return;
                }

                //                if (TextUtils.isEmpty(getView().getPrenatalProjectId())) {
                //                    ToastUtils.showShort("请选择检查项目");
                //                    return;
                //                }

                if (TextUtils.isEmpty(getView().getSelectedDate())) {
                    ToastUtils.showShort("请选择日期");
                    return;
                }

                if (TextUtils.isEmpty(getView().getTimeInterval())) {
                    ToastUtils.showShort("请选择时间段");
                    return;
                }

                commitPregnancy();
                break;
            default:
                break;
        }
    }


    private void showDateDialog(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(DateUtils.YYYY_MM_DD);
        Date date;
        try {
            if (!TextUtils.isEmpty(dateStr)) {
                date = formatter.parse(dateStr);
            } else {
                date = new Date();
            }
            Calendar startDate = Calendar.getInstance();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            TimePickerView pvTime = new TimePickerBuilder(getContext(),
                    (date1, v) -> getView().setOrderDate(date1))
                    .setDate(calendar)
                    .setRangDate(startDate, null)
                    .build();
            pvTime.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取检查项目
     */
    public void getCheckProjects() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PregnancyService.class).getCheckProjects(), new HttpSubscriber<CheckProjectRootBean>(getContext(), getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable CheckProjectRootBean data) {
                getView().getCheckProjectsSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getCheckProjectsFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 获取检查时间
     */
    public void getCheckProjectsTimes(String date,String state) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PregnancyService.class).getCheckProjectsTimes(date,state), new HttpSubscriber<List<PregnancyOrderTime>>(getContext(), getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable List<PregnancyOrderTime> data) {
                if (data != null) {
                    getView().getCheckProjectsTimesSuccess(data);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getCheckProjectsTimesFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 续约
     */
    private void commitPregnancy() {
        Map<String, Object> map = new HashMap<>();
        map.put("recordId", getView().getRecordID());
        map.put("appointType", getView().getAppointType());
        //        map.put("prenatalProjectId", getView().getPrenatalProjectId());
        //        map.put("prenatalProjectName", getView().getPrenatalProjectName());
        map.put("prenatalDate", getView().getSelectedDate());
        map.put("timeInterval", getView().getTimeInterval());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PregnancyService.class).commitPregnancy(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(), getDisposable(), true, false) {

            @Override
            public void requestComplete(@Nullable Object data) {
                getView().commitPregnancySuccess(data);
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
