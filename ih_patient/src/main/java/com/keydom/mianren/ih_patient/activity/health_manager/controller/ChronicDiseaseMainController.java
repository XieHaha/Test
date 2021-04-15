package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.HealthConsultantActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.HealthSummaryActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.InterventionPlanActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.LifestyleMainActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.view.ChronicDiseaseMainView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.HealthDataBean;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.net.ChronicDiseaseService;
import com.keydom.mianren.ih_patient.utils.DateUtils;
import com.keydom.mianren.ih_patient.view.HealthDataEditDialog;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 慢病管理
 */
public class ChronicDiseaseMainController extends ControllerImpl<ChronicDiseaseMainView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.disease_main_day_status_tv:
                HealthDataEditDialog dialog = new HealthDataEditDialog(getContext(),
                        getView().getHealthDataBean(), getView().getChronicDiseaseType(),
                        (bean) -> insertOrUpdateHeathValue(bean));
                dialog.show();
                break;
            case R.id.disease_main_last_day_iv:
                if (v.isSelected()) {
                    getView().setNewDate(-1);
                }
                break;
            case R.id.disease_main_next_day_iv:
                if (v.isSelected()) {
                    getView().setNewDate(1);
                }
                break;
            case R.id.disease_main_eat_record_layout:
                LifestyleMainActivity.start(getContext(), getView().getCalendar(),
                        getView().getPatientId(),
                        LifestyleMainActivity.LIFESTYLE_DIET);
                break;
            case R.id.disease_main_sleep_record_layout:
                LifestyleMainActivity.start(getContext(), getView().getCalendar(),
                        getView().getPatientId(),
                        LifestyleMainActivity.LIFESTYLE_SLEEP);
                break;
            case R.id.disease_main_sports_record_layout:
                LifestyleMainActivity.start(getContext(), getView().getCalendar(),
                        getView().getPatientId(),
                        LifestyleMainActivity.LIFESTYLE_SPORTS);
                break;
            case R.id.disease_main_intervention_plan_layout:
                InterventionPlanActivity.start(getContext(), getView().getPatientId());
                break;
            case R.id.disease_main_health_summary_layout:
                HealthSummaryActivity.start(getContext(), getView().getPatientId());
                break;
            case R.id.disease_main_contact_counselor_tv:
                HealthConsultantActivity.start(getContext(), getView().getPatientId());
                break;
            default:
                break;
        }
    }

    /**
     * 获取健康值
     */
    public void getHeathValue(String patientId, String curSelectDate) {
        Map<String, String> params = new HashMap<>(16);
        params.put("writeDate", DateUtils.transDate(curSelectDate, DateUtils.YYYY_MM_DD_CH,
                DateUtils.YYYY_MM_DD));
        params.put("patientId", patientId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).getHeathValue(params), new HttpSubscriber<HealthDataBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable HealthDataBean data) {
                getView().requestHealthDataSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 新增或者修改健康值
     */
    public void insertOrUpdateHeathValue(HealthDataBean bean) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).insertOrUpdateHeathValue(HttpService.INSTANCE.object2Body(getView().getUpdateHealthDataParams(bean))), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().updateHeathValueSuccess(bean);
                //更新健康管理首页
                EventBus.getDefault().post(new Event(EventType.UPDATE_HEALTH_MANAGER, null));
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
