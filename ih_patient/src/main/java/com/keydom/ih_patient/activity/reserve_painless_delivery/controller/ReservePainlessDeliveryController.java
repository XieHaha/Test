package com.keydom.ih_patient.activity.reserve_painless_delivery.controller;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.diagnose_user_manager.ManageUserSelectActivity;
import com.keydom.ih_patient.activity.reserve_painless_delivery.view.ReservePainlessDeliveryView;
import com.keydom.ih_patient.bean.ManagerUserBean;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.PainlessDeliveryService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 无痛分泌预约控制器
 */
public class ReservePainlessDeliveryController extends ControllerImpl<ReservePainlessDeliveryView> implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_visit:
                ManageUserSelectActivity.start(getContext(), getView().getCurUserId());
                break;
            case R.id.layout_fetus:
                break;
            case R.id.layout_menstruation:
                KeyboardUtils.hideSoftInput((Activity) getContext());
                TimePickerView pvTime = new TimePickerBuilder(getContext(),
                        (date, v1) -> getView().setMenstruation(date)).build();
                pvTime.show();
                break;
            case R.id.layout_due_date:
                KeyboardUtils.hideSoftInput((Activity) getContext());
                TimePickerView pickerView = new TimePickerBuilder(getContext(),
                        (date, v12) -> getView().setDueDate(date)).build();
                pickerView.show();
                break;
            case R.id.layout_select:
                getView().setSelect();
                break;
            case R.id.tv_note:
                break;
            case R.id.tx_next:
                onNext();
                break;
            default:
                break;
        }

    }

    private void onNext() {
        ManagerUserBean bean = getView().getVisitUser();
        if (bean == null) {
            ToastUtil.showMessage(getContext(), "请选择就诊人");
            return;
        }
        if (TextUtils.isEmpty(getView().getAge())) {
            ToastUtil.showMessage(getContext(), "请输入年龄");
            return;
        }
        if (TextUtils.isEmpty(getView().getLastDate())) {
            ToastUtil.showMessage(getContext(), "请选择末次月经时间");
            return;
        }
        if (TextUtils.isEmpty(getView().getDueDate())) {
            ToastUtil.showMessage(getContext(), "请选择预产期");
            return;
        }
        //        if (TextUtils.isEmpty(getView().getFetus())) {
        //            ToastUtil.showMessage(getContext(), "胎数不能为空");
        //            return;
        //        }
        if (TextUtils.isEmpty(getView().getPhone())) {
            ToastUtil.showMessage(getContext(), "电话号码不能为空");
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("age", Integer.valueOf(getView().getAge()));
        //胎数
        map.put("embryoNumber", 1);
        map.put("hospitalId", App.hospitalId);
        map.put("patientId", bean.getId());
        map.put("registerUserId", Global.getUserId());
        //预约时间
        map.put("appointmentDate", getView().getDueDate());
        map.put("eleCardNumber", bean.getCardId());
        map.put("expectedDateOfConfinement", getView().getDueDate());
        map.put("lastMenstrualPeriodTime", getView().getLastDate());
        map.put("patientName", bean.getName());
        map.put("phoneNumber", getView().getPhone());

        //预约
        commitPainlessDelivery(map);
    }

    /**
     * 无痛分娩预约
     */
    private void commitPainlessDelivery(Map<String, Object> map) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PainlessDeliveryService.class).commitPainlessDelivery(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().reserveSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().reserveFailed();
                return super.requestError(exception, code, msg);
            }
        });

    }
}
