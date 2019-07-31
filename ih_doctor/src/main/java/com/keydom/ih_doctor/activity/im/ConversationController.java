package com.keydom.ih_doctor.activity.im;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.bean.InquiryBean;
import com.keydom.ih_doctor.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;

public class ConversationController extends ControllerImpl<ConversationView> {

    public void getInquiryStatus() {
        if (getView().isGetStatus()) {
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(InquiryService.class).getOrderDetails(getView().getUserId(), "0"),
                    new HttpSubscriber<InquiryBean>(getContext(), getDisposable(), true, false) {
                        @Override
                        public void requestComplete(@Nullable InquiryBean data) {
                            getView().loadSuccess(data);
                        }
                    });
        }
    }

    public void acceptInquisition() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", getView().getId());
        RequestBody body = HttpService.INSTANCE.object2Body(map);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(InquiryService.class).acceptInquisition(body),
                new HttpSubscriber<Object>(getContext(), getDisposable(), true, false) {
                    @Override
                    public void requestComplete(@Nullable Object data) {
                        getView().acceptSuccess();
                    }

                    @Override
                    public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                        if (code == 300) {
                            getView().acceptFailed(msg);
                            return true;
                        }
                        ToastUtil.shortToast(getContext(), msg);
                        return false;
                    }
                });
    }

    public void endInquisition() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", getView().getId());
        RequestBody body = HttpService.INSTANCE.object2Body(map);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(InquiryService.class).endInquisition(body),
                new HttpSubscriber<Object>(getContext(), getDisposable(), true, false) {
                    @Override
                    public void requestComplete(@Nullable Object data) {
                        getView().endSuccess();
                    }

                    @Override
                    public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                        ToastUtil.shortToast(getContext(), msg);
                        return super.requestError(exception, code, msg);
                    }
                });

    }

    public void stopReferral(){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(InquiryService.class).stopReferral(getView().getId()),
                new HttpSubscriber<Object>(getContext(), getDisposable(), true, false) {
                    @Override
                    public void requestComplete(@Nullable Object data) {
                        getView().stopReferralSuccess();
                    }

                    @Override
                    public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                        ToastUtil.shortToast(getContext(), msg);
                        return super.requestError(exception, code, msg);
                    }
                });
    }
}
