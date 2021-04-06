package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.LifestyleDataActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.view.LifestyleMainView;
import com.keydom.mianren.ih_patient.bean.EatRecordBean;
import com.keydom.mianren.ih_patient.net.ChronicDiseaseService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 生活记录
 */
public class LifestyleMainController extends ControllerImpl<LifestyleMainView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lifestyle_main_last_day_iv:
                if (v.isSelected()) {
                    getView().setNewDate(-1);
                }
                break;
            case R.id.lifestyle_main_next_day_tv:
                if (v.isSelected()) {
                    getView().setNewDate(1);
                }
                break;
            case R.id.lifestyle_bottom_cancel_tv:
                break;
            case R.id.lifestyle_bottom_submit_tv:
                break;
            case R.id.view_eat_record_add_breakfast_tv:
            case R.id.eat_record_breakfast_add_tv:
                LifestyleDataActivity.start(getContext(), 1, 0, getView().getCurSelectDate(),
                        getView().getEatRecordBean(), getView().getPatientId());
                break;
            case R.id.view_eat_record_add_lunch_tv:
            case R.id.eat_record_lunch_add_tv:
                LifestyleDataActivity.start(getContext(), 1, 1, getView().getCurSelectDate(),
                        getView().getEatRecordBean(), getView().getPatientId());
                break;
            case R.id.view_eat_record_add_dinner_tv:
            case R.id.eat_record_dinner_add_tv:
                LifestyleDataActivity.start(getContext(), 1, 2, getView().getCurSelectDate(),
                        getView().getEatRecordBean(), getView().getPatientId());
                break;
            case R.id.view_eat_record_add_extra_tv:
            case R.id.eat_record_extra_add_tv:
                LifestyleDataActivity.start(getContext(), 1, 3, getView().getCurSelectDate(),
                        getView().getEatRecordBean(), getView().getPatientId());
                break;
            case R.id.lifestyle_main_copy_tv:
                //复用到今日
                if (getView().isNotToday()) {
                    insertOrUpdateFoodRecord();
                }
                break;
            case R.id.view_eat_record_add_breakfast_iv:
                getView().expandBreakfastLayout();
                break;
            case R.id.view_eat_record_add_lunch_iv:
                getView().expandLunchLayout();
                break;
            case R.id.view_eat_record_add_dinner_iv:
                getView().expandDinnerLayout();
                break;
            case R.id.view_eat_record_add_extra_iv:
                getView().expandExtraLayout();
                break;
            default:
                break;
        }
    }

    /**
     * 查询患者就餐记录
     */
    public void foodRecordList(String patientId, String curSelectDate) {
        Map<String, String> params = new HashMap<>(16);
        params.put("time", curSelectDate);
        params.put("patientId", patientId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).foodRecordList(params), new HttpSubscriber<EatRecordBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable EatRecordBean data) {
                getView().requestFoodRecordSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().requestFoodRecordFailed();
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 新增或者修改就餐记录
     */
    public void insertOrUpdateFoodRecord() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).insertOrUpdateFoodRecord(HttpService.INSTANCE.object2Body(getView().getParams())), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().copyFoodRecordSuccess();
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
     * 删除就餐记录
     */
    public void deleteFoodRecord(String id) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).deleteFoodRecord(id), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
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
