package com.keydom.mianren.ih_doctor.activity.im;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.SpeakLimitBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
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

public class ConversationController extends ControllerImpl<ConversationView> {

    /**
     * 获取发言权限
     */
    public void getDoctorLimit() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(InquiryService.class).getDoctorLimit(String.valueOf(getView().getOrderId())),
                new HttpSubscriber<SpeakLimitBean>(getContext(), getDisposable(), false) {
                    @Override
                    public void requestComplete(@Nullable SpeakLimitBean data) {
                        getView().getDoctorLimitSuccess(data);
                    }
                });
    }

    /**
     * 获取订单详情
     */
    public void getInquiryStatus() {
        if (getView().isGetStatus()) {
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(InquiryService.class).getOrderDetails(getView().getUserId(), getView().getOrderId(), "0"),
                    new HttpSubscriber<InquiryBean>(getContext(), getDisposable(), true, false) {
                        @Override
                        public void requestComplete(@Nullable InquiryBean data) {
                            getView().loadSuccess(data);
                        }
                    });
        }
    }

    /**
     * 接诊
     */
    public void acceptInquisition() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", getView().getOrderId());
        RequestBody body = HttpService.INSTANCE.object2Body(map);
        if (getView().isTeam()) {
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(InquiryService.class).pcAcceptInquisition(body),
                    new HttpSubscriber<Object>(getContext(), getDisposable(), true, false) {
                        @Override
                        public void requestComplete(@Nullable Object data) {
                            getView().acceptSuccess();
                        }

                        @Override
                        public boolean requestError(@NotNull ApiException exception, int code,
                                                    @NotNull String msg) {
                            if (code == 300) {
                                getView().acceptFailed(msg);
                                return true;
                            }
                            ToastUtil.showMessage(getContext(), msg);
                            return false;
                        }
                    });
        } else {
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(InquiryService.class).acceptInquisition(body),
                    new HttpSubscriber<Object>(getContext(), getDisposable(), true, false) {
                        @Override
                        public void requestComplete(@Nullable Object data) {
                            getView().acceptSuccess();
                        }

                        @Override
                        public boolean requestError(@NotNull ApiException exception, int code,
                                                    @NotNull String msg) {
                            if (code == 300) {
                                getView().acceptFailed(msg);
                                return true;
                            }
                            ToastUtil.showMessage(getContext(), msg);
                            return false;
                        }
                    });
        }
    }

    /**
     * 结束问诊
     */
    public void endInquisition() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", getView().getOrderId());
        RequestBody body = HttpService.INSTANCE.object2Body(map);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(InquiryService.class).endInquisition(body),
                new HttpSubscriber<Object>(getContext(), getDisposable(), true, false) {
                    @Override
                    public void requestComplete(@Nullable Object data) {
                        getView().endSuccess();
                    }

                    @Override
                    public boolean requestError(@NotNull ApiException exception, int code,
                                                @NotNull String msg) {
                        ToastUtil.showMessage(getContext(), msg);
                        return super.requestError(exception, code, msg);
                    }
                });

    }

    public void stopReferral() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(InquiryService.class).stopReferral(getView().getOrderId()),
                new HttpSubscriber<Object>(getContext(), getDisposable(), true, false) {
                    @Override
                    public void requestComplete(@Nullable Object data) {
                        getView().stopReferralSuccess();
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
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ConsultationService.class).uploadDiagnosisVoice(HttpService.INSTANCE.object2Body(params)), new HttpSubscriber<String>(mContext, getDisposable(), true, false) {
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
