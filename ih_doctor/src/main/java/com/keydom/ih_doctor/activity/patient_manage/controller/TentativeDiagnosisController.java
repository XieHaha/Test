package com.keydom.ih_doctor.activity.patient_manage.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_common.view.SwitchButton;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.doctor_cooperation.SelectDoctorActivity;
import com.keydom.ih_doctor.activity.patient_manage.ChoosePatientActivity;
import com.keydom.ih_doctor.activity.patient_manage.TentativeDiagnosisActivity;
import com.keydom.ih_doctor.activity.patient_manage.view.TentativeDiagnosisView;
import com.keydom.ih_doctor.bean.WarrantDetailBean;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.net.PatientManageApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TentativeDiagnosisController extends ControllerImpl<TentativeDiagnosisView> implements View.OnClickListener, IhTitleLayout.OnRightTextClickListener, SwitchButton.OnCheckedChangeListener {
    @SingleClick(1000)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_patient_tv:
                ChoosePatientActivity.start(getContext(), getView().getSelectPatient());
                break;
            case R.id.choose_doctor_tv:

                SelectDoctorActivity.startActivityOnlyResult(getContext());

                break;
            case R.id.inquiry_scaler_minus_layout:
                getView().inquiryScalerMinus();
                break;
            case R.id.inquiry_scaler_add_layout:
                getView().inquiryScalerAdd();
                break;
            case R.id.group_doctor:
//                SelectDoctorActivity.startActivitySelfDeptOnlyResult(getContext());
                if (getView().getType() == TentativeDiagnosisActivity.TYPECREAT)
                    SelectDoctorActivity.startActivityOfGroupMemberOnlyResult(getContext());
                else
                    ToastUtil.showMessage(getContext(), "当前不能切换其他医生");
                break;
            case R.id.patient_name:
                ChoosePatientActivity.start(getContext(), getView().getSelectPatient());
                break;
            case R.id.choose_term_tv:
                getView().showTimePikerDialog();
                break;
            default:
        }
    }

    @Override
    public void OnRightTextClick(View v) {
        if (getView().submitCheck()) {
            addRegisterUserAuthorizationService();
        }
    }


    /**
     * 授权
     */
    public void addRegisterUserAuthorizationService() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PatientManageApiService.class).addRegisterUserAuthorizationService(HttpService.INSTANCE.object2Body(getView().getAuthorizeMap())), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().authSuccess(data);
                hideLoading();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().authFailed(msg);
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取授权信息
     *
     * @param doctorId
     */
    public void getWarrantDetail(String doctorId,String userId) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PatientManageApiService.class).getEditInfo(MyApplication.userInfo.getId(), doctorId,userId), new HttpSubscriber<WarrantDetailBean>() {
            @Override
            public void requestComplete(@Nullable WarrantDetailBean data) {
                getView().getWarrantDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getWarrantDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onCheckedChanged(SwitchButton buttonView, boolean isChecked) {
        getView().jurisdictionIsOpen(isChecked);
    }
}
