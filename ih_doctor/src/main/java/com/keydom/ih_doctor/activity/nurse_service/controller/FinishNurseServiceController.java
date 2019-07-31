package com.keydom.ih_doctor.activity.nurse_service.controller;

import android.view.View;

import com.bigkoo.pickerview.view.OptionsPickerView;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.nurse_service.MaterialChooseActivity;
import com.keydom.ih_doctor.activity.nurse_service.view.FinishNurseServiceView;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.net.NurseServiceApiService;
import com.keydom.ih_doctor.utils.DateUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Name：com.keydom.ih_doctor.fragment.controller
 * @Author：song
 * @Date：18/11/16 下午2:26
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:26
 */
public class FinishNurseServiceController extends ControllerImpl<FinishNurseServiceView> implements View.OnClickListener {

    private OptionsPickerView optionsPickerView;
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nurse_service_add_medicine:
                MaterialChooseActivity.start(getContext(), getView().getSelectMaterial());
                break;
            case R.id.next_visit_time:
                chooseTime();
                break;
            case R.id.finish_service_bt:
                if (getView().finishCheck()) {
                    NurseFinishOrderOrAndItem();
                }
                break;
        }
    }


    /**
     * 选择下次服务上门时间
     */
    private void chooseTime() {
        if (optionsPickerView == null) {
            optionsPickerView = DateUtils.showDateIntervalChooseDialog(getContext(), 14, 0, 24, (date, startTime, endTime) ->
                    getView().timeChoose(date, startTime, endTime));
        }
        optionsPickerView.show();
    }


    /**
     * 完成服务
     */
    public void NurseFinishOrderOrAndItem() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).NurseFinishOrderOrAndItem(HttpService.INSTANCE.object2Body(getView().getFinishMap())), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().finishSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().finishFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
