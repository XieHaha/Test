package com.keydom.mianren.ih_patient.activity.hospital_payment.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.hospital_payment.view.HospitalPaymentView;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.ChoosePatientActivity;
import com.keydom.mianren.ih_patient.bean.HospitalRecordRootBean;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.CardService;
import com.keydom.mianren.ih_patient.net.HospitalPaymentService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * 住院预缴金
 */
public class HospitalPaymentController extends ControllerImpl<HospitalPaymentView> implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hospital_payment_patient_layout:
                ChoosePatientActivity.start(getContext(), -1, false);
                break;
            default:
                break;
        }
    }

    /**
     * 查询所有就诊卡
     */
    public void queryAllCard() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", Global.getUserId());
        map.put("hospital", App.hospitalId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(CardService.class).getCardList(map), new HttpSubscriber<List<MedicalCardInfo>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<MedicalCardInfo> data) {
                getView().getAllCardSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取患者住院信息和订单信息
     */
    public void getHospitalPayment() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HospitalPaymentService.class).getInHospitalRecord(getView().getMedicalCardNumber()), new HttpSubscriber<HospitalRecordRootBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable HospitalRecordRootBean data) {
                getView().fillHospitalPaymentData(data);
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
     * 创建住院预缴订单
     */
    public void createInHospitalOrder() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("eleCardNumber", Global.getUserId());
        map.put("fee", App.hospitalId);
        map.put("inHospitalNo", App.hospitalId);
        map.put("patientId", getView().getMedicalCardInfo().getId());
        map.put("patientName", getView().getMedicalCardInfo().getName());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HospitalPaymentService.class).createInHospitalOrder(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<List<MedicalCardInfo>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable List<MedicalCardInfo> data) {
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }
}
