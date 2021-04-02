package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
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
            case R.id.disease_main_data_hint_tv:
                break;
            case R.id.lifestyle_bottom_cancel_tv:
                break;
            case R.id.lifestyle_bottom_submit_tv:
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
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 新增或者修改就餐记录
     */
    public void insertOrUpdateFoodRecord(EatRecordBean bean) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).insertOrUpdateFoodRecord(HttpService.INSTANCE.object2Body(getView().getUpdateEatDataParams(bean))), new HttpSubscriber<EatRecordBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable EatRecordBean data) {
                getView().updateFoodRecordSuccess(bean);
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
