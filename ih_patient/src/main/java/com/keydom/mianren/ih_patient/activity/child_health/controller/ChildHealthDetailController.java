package com.keydom.mianren.ih_patient.activity.child_health.controller;

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
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.child_health.view.ChildHealthDetailView;
import com.keydom.mianren.ih_patient.net.ChildHealthService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;

/**
 * @author 顿顿
 * @date 20/2/27 11:39
 * @des 儿童保健预约详情控制器
 */
public class ChildHealthDetailController extends ControllerImpl<ChildHealthDetailView> implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.child_health_detail_select_time_tv) {
            KeyboardUtils.hideSoftInput((Activity) getContext());
            TimePickerView pickerView = new TimePickerBuilder(getContext(),
                    (date, v12) -> getView().setReserveDate(date)).setRangDate(Calendar.getInstance(), null).build();
            pickerView.show();
        } else if (v.getId() == R.id.child_health_detail_next_tv) {
            if (getView().commitAble()) {
                childAppointSubmit();
            }
        }
    }


    /**
     * 获取儿童保健记录
     */
    public void childAppointSubmit() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChildHealthService.class).childAppointSubmit(HttpService.INSTANCE.object2Body(getView().getParams())), new HttpSubscriber<String>(getContext(), getDisposable(), true, false) {

            @Override
            public void requestComplete(@Nullable String data) {
                getView().applySuccess();
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
