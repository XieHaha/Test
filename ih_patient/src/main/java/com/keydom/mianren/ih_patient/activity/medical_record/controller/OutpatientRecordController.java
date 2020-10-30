package com.keydom.mianren.ih_patient.activity.medical_record.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.inspection_report.InspectionReportActivity;
import com.keydom.mianren.ih_patient.activity.medical_record.MedicalRecordActivity;
import com.keydom.mianren.ih_patient.activity.medical_record.view.OutpatientRecordView;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.ChoosePatientActivity;
import com.keydom.mianren.ih_patient.activity.pregnancy.PregnancyRecordActivity;
import com.keydom.mianren.ih_patient.activity.prescription_check.PrescriptionRecordActivity;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.net.CardService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * created date: 2019/1/4 on 13:04
 * des:处方记录控制器
 *
 * @author 顿顿
 */
public class OutpatientRecordController extends ControllerImpl<OutpatientRecordView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.medical_record_change_tv:
                ChoosePatientActivity.start(getContext(), -1, false);
                break;
            case R.id.medical_record_inspection:
                if (getView().getMedicalCardInfo() == null) {
                    ToastUtil.showMessage(getContext(), "请选择就诊人");
                    return;
                }
                PregnancyRecordActivity.start(getContext(), getView().getMedicalCardInfo());
                break;
            case R.id.medical_record_medical:
                if (getView().getMedicalCardInfo() == null) {
                    ToastUtil.showMessage(getContext(), "请选择就诊人");
                    return;
                }
                MedicalRecordActivity.start(getContext(), getView().getMedicalCardInfo());
                break;
            case R.id.medical_record_examine:
                if (getView().getMedicalCardInfo() == null) {
                    ToastUtil.showMessage(getContext(), "请选择就诊人");
                    return;
                }
                InspectionReportActivity.start(getContext(), getView().getMedicalCardInfo(),
                        Type.INSPECTIONTYPE);
                break;
            case R.id.medical_record_check:
                if (getView().getMedicalCardInfo() == null) {
                    ToastUtil.showMessage(getContext(), "请选择就诊人");
                    return;
                }
                InspectionReportActivity.start(getContext(), getView().getMedicalCardInfo(),
                        Type.BODYCHECKTYPE);
                break;
            case R.id.medical_record_prescription:
                if (getView().getMedicalCardInfo() == null) {
                    ToastUtil.showMessage(getContext(), "请选择就诊人");
                    return;
                }
                PrescriptionRecordActivity.start(getContext(), getView().getMedicalCardInfo());
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
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(CardService.class).getCardList(map), new HttpSubscriber<List<MedicalCardInfo>>(getContext(), getDisposable(), true) {
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
}
