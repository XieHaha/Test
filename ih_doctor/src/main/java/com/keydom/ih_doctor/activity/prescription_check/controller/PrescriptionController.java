package com.keydom.ih_doctor.activity.prescription_check.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.prescription_check.view.PrescriptionView;
import com.keydom.ih_doctor.bean.PrescriptionDetailBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.m_interface.OnCheckDialogListener;
import com.keydom.ih_doctor.net.PrescriptionService;
import com.keydom.ih_doctor.utils.DialogUtils;
import com.keydom.ih_doctor.utils.SignUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class PrescriptionController extends ControllerImpl<PrescriptionView> implements View.OnClickListener {


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_yes:
                SignUtils.sign(getContext(), getView().getAuditMap().toString(), Const.SIGN_CHECK_PRESCRIPTION, new SignUtils.SignCallBack() {
                    @Override
                    public void signSuccess(String signature, String jobId) {
                        getView().auditPass(signature, jobId);
                    }
                });

                break;
            case R.id.check_no:
                DialogUtils.createCheckDialog(getContext(), new OnCheckDialogListener() {
                    @Override
                    public void commit(View v, String value) {
                        SignUtils.sign(getContext(), getView().getAuditMap().toString(), Const.SIGN_CHECK_PRESCRIPTION, new SignUtils.SignCallBack() {
                            @Override
                            public void signSuccess(String signature, String jobId) {
                                getView().auditReturn(value, signature, jobId);
                            }
                        });

                    }
                }).show();


                break;
        }

    }

    /**
     * 获取处方详情
     */
    public void getPrescriptionDetail() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getDrugControlPrescriptionDetail(getView().getDetailMap()), new HttpSubscriber<PrescriptionDetailBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PrescriptionDetailBean data) {
                hideLoading();
                getView().getDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }


    /**
     * 药师处方审核
     */
    public void audit(String signature, String jobId) {
        showLoading();
        Map<String, Object> map = getView().getAuditMap();
        map.put("signature", signature);
        map.put("signJobId", jobId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).audit(HttpService.INSTANCE.object2Body(getView().getAuditMap())), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().auditSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().auditFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }


}
