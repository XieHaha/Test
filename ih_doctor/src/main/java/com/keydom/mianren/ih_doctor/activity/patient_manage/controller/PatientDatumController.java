package com.keydom.mianren.ih_doctor.activity.patient_manage.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.CommonInputActivity;
import com.keydom.mianren.ih_doctor.activity.patient_manage.PatientDatumActivity;
import com.keydom.mianren.ih_doctor.activity.patient_manage.view.PatientDatumView;
import com.keydom.mianren.ih_doctor.bean.PatientInfoBean;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.PatientManageApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class PatientDatumController extends ControllerImpl<PatientDatumView> implements View.OnClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.lable_tv:
                CommonInputActivity.start(getContext(), PatientDatumActivity.PATIENT_TAG, "添加患者标签", "", 10);
                break;
            case R.id.remark:
                CommonInputActivity.start(getContext(), PatientDatumActivity.PATIENT_REMARK, "修改患者备注", getView().getRemark(), 50);
                break;
            case R.id.choose_doctor_tv:
//                SelectDoctorActivity.startActivityOnlyResult(getContext());
                break;
        }

    }


    /**
     * 获取患者信息
     */
    public void getPatientInfo() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PatientManageApiService.class).getRegUserInfo(getView().getPatientInfoMap()), new HttpSubscriber<PatientInfoBean>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable PatientInfoBean data) {
                getView().getInfoSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getInfoFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 修改患者信息
     *
     * @param map
     */
    public void updateRegUserInfo(Map<String, Object> map) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PatientManageApiService.class).updateRegUserInfo(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                getView().updatePatientInfoSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().updatePatientInfoFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
