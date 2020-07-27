package com.keydom.mianren.ih_doctor.activity.prescription_check.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.prescription_check.view.PrescriptionView;
import com.keydom.mianren.ih_doctor.bean.PrescriptionDetailBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.m_interface.OnCheckDialogListener;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.PrescriptionService;
import com.keydom.mianren.ih_doctor.net.SignService;
import com.keydom.mianren.ih_doctor.utils.DialogUtils;
import com.keydom.mianren.ih_doctor.utils.SignUtils;

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

    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_yes:
                //getView().auditPass("", "");
                SignUtils.sign(getContext(), getView().getAuditMap().toString()
                        , Const.SIGN_CHECK_PRESCRIPTION, new SignUtils.SignCallBack() {
                            @Override
                            public void signSuccess(String signature, String jobId) {
                                caCount(2);
                                getView().auditPass(signature, jobId);
                            }
                        });

                break;
            case R.id.check_no:
                DialogUtils.createCheckDialog(getContext(), new OnCheckDialogListener() {
                    @Override
                    public void commit(View v, String value) {
                        //       getView().auditReturn(value, "", "");
                        SignUtils.sign(getContext(), getView().getAuditMap().toString(),
                                Const.SIGN_CHECK_PRESCRIPTION, new SignUtils.SignCallBack() {
                                    @Override
                                    public void signSuccess(String signature, String jobId) {
                                        caCount(3);
                                        getView().auditReturn(value, signature, jobId);
                                    }
                                });
                    }
                }).show();
                break;
            default:
                break;
        }

    }

    /**
     * 获取处方详情
     */
    public void getPrescriptionDetail() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getDrugControlPrescriptionDetail(getView().getDetailMap()), new HttpSubscriber<PrescriptionDetailBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable PrescriptionDetailBean data) {
                getView().getDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }


    /**
     * 药师处方审核
     */
    public void audit(String signature, String jobId) {
        Map<String, Object> map = getView().getAuditMap();
        map.put("signature", signature);
        map.put("signJobId", jobId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).audit(HttpService.INSTANCE.object2Body(getView().getAuditMap())), new HttpSubscriber<String>(getContext(), getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().auditSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().auditFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * ca统计
     */
    private void caCount(int type) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(SignService.class).caCount(type), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }
}
