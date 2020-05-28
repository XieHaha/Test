package com.keydom.mianren.ih_doctor.activity.online_consultation.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationRoomView;
import com.keydom.mianren.ih_doctor.bean.AuditInfoBean;
import com.keydom.mianren.ih_doctor.net.ConsultationService;
import com.keydom.mianren.ih_doctor.net.MainApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ConsultationRoomController extends ControllerImpl<ConsultationRoomView> {
    /**
     * 申请加入会诊
     */
    public void applyJoinConsultation() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ConsultationService.class).applyJoinConsultation(HttpService.INSTANCE.object2Body(getView().getApplyParams())), new HttpSubscriber<String>(mContext, getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().applyJoinConsultationSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().applyJoinConsultationFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 处理会诊加入申请
     */
    public void dealConsultationApply(AuditInfoBean bean) {
        Map<String, Object> params = new HashMap<>();
        params.put("accept", true);
        params.put("auditId", bean.getAuditorId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ConsultationService.class).dealConsultationApply(HttpService.INSTANCE.object2Body(params)), new HttpSubscriber<String>(mContext, getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().dealConsultationApplySuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(mContext, msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 结束会诊
     */
    public void endConsultationOrder(String recordId, String videoUrl) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ConsultationService.class).endConsultationOrder(recordId, videoUrl), new HttpSubscriber<String>(mContext, getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().endConsultationSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().endConsultationFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 上传file  视频音频录制
     */
    public void uploadVoiceFile(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/otcet-stream"),
                file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(),
                requestFile);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MainApiService.class).upload(body), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                uploadConsultationVoice(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(mContext, msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 上传会诊语音
     */
    private void uploadConsultationVoice(String voiceUrl) {
        Map<String, Object> params = getView().getUploadVoiceParams();
        params.put("url", voiceUrl);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ConsultationService.class).uploadConsultationVoice(HttpService.INSTANCE.object2Body(params)), new HttpSubscriber<String>(mContext, getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable String data) {
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(mContext, msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
