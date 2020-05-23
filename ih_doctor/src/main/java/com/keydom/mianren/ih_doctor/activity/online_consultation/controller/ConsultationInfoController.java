package com.keydom.mianren.ih_doctor.activity.online_consultation.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.DiagnoseOrderDetailBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.ApiService;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationInfoView;
import com.keydom.mianren.ih_doctor.bean.ConsultationDetailBean;
import com.keydom.mianren.ih_doctor.net.ConsultationService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @date 20/4/8 14:39
 * @des 会诊室-病历资料
 */
public class ConsultationInfoController extends ControllerImpl<ConsultationInfoView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.consultation_info_apply_layout:
                getView().onApplyInfoSelect();
                break;
            case R.id.consultation_info_medical_layout:
                getView().onMedicalSelect();
                break;
            case R.id.consultation_info_report_layout:
                getView().onReportInfoSelect();
                break;
            case R.id.consultation_info_video_layout:
                getView().onVideoSelect();
                break;
            default:
                break;
        }
    }

    /**
     * 获取会诊详情
     */
    public void getConsultationOrderDetail(String id) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ConsultationService.class).consultationOrderInfo(id), new HttpSubscriber<ConsultationDetailBean>(mContext, getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable ConsultationDetailBean data) {
                getView().requestInfoSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().requestInfoFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
    /**
     * 获取会诊详情
     */
    public void getPatientInquisitionById(String id) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).getPatientInquisitionById(id), new HttpSubscriber<DiagnoseOrderDetailBean>(mContext, getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable DiagnoseOrderDetailBean data) {
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }
}
