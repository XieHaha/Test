package com.keydom.mianren.ih_patient.activity.pregnancy.controller;

import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.factory.NoJsonConverterFactory;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.pregnancy.view.PregnancyReserveView;
import com.keydom.mianren.ih_patient.bean.CheckProjectRootBean;
import com.keydom.mianren.ih_patient.bean.DoctorScheduling;
import com.keydom.mianren.ih_patient.bean.PregnancyOrderTime;
import com.keydom.mianren.ih_patient.net.PregnancyService;
import com.keydom.mianren.ih_patient.utils.DateUtils;
import com.keydom.mianren.ih_patient.view.DoctorSchedualDialog;
import com.keydom.mianren.ih_patient.view.TimeSchedualDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author 顿顿
 */
public class PregnancyReserveController extends ControllerImpl<PregnancyReserveView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pregnancy_order_date_root_rl:
                showDateDialog(getView().getSelectedDate());
                break;
            case R.id.pregnancy_check_projects_root_rl:
                //                ChooseInspectItemActivity.start(getContext(), getView()
                //                .getCheckProjects(),
                //                        getView().getSelectSubBeans());
                DoctorSchedualDialog dialog = new DoctorSchedualDialog(getContext(), null);
                dialog.setDataList(getView().getDoctorSchedulings());
                dialog.show();
                break;
            case R.id.nice_spinner:
                TimeSchedualDialog timeDialog = new TimeSchedualDialog(getContext(),
                        new TimeSchedualDialog.OnSelectListener() {
                            @Override
                            public void onSelected(PregnancyOrderTime bean) {
                                getView().setPregnancyOrderTime(bean);
                            }
                        });
                timeDialog.setDataList(getView().getSpinnerTimeData());
                timeDialog.show();
                break;
            case R.id.pregnancy_detail_order_tv:
                if (TextUtils.isEmpty(getView().getRecordID())) {
                    ToastUtils.showShort("请确认是否存在该预约");
                    return;
                }

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
     * 获取排班医生
     */
    public void getDoctorScheduling() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PregnancyService.class).getDoctorScheduling(), new HttpSubscriber<List<DoctorScheduling>>(getContext(), getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable List<DoctorScheduling> data) {
                getView().requestDoctorSchedulingSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().requestDoctorSchedulingFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取排班医生
     */
    public void getAntDoctor(String date, String timeInterval) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.RELEASE_HOST)
                .addConverterFactory(NoJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        PregnancyService myInterface = retrofit.create(PregnancyService.class);
        Call<Object> call = myInterface.getAntDoctor(SharePreferenceManager.getToken(),date,timeInterval);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                getView().requestDoctorSuccess((String) response.body());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
            }
        });
    }


    /**
     * 获取检查时间
     */
    public void getCheckProjectsTimes(String date, int state) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PregnancyService.class).getCheckProjectsTimes(date, state), new HttpSubscriber<List<PregnancyOrderTime>>(getContext(), getDisposable(), true, false) {
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
        map.put("appointType", getView().getmPrenancyType());
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
