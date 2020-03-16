package com.keydom.mianren.ih_patient.activity.certification.controller;

import android.text.TextUtils;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.PhoneUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.certification.view.CertificateView;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.LoginService;
import com.keydom.mianren.ih_patient.net.UploadService;
import com.keydom.mianren.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 验证控制器
 */
public class CertificateController extends ControllerImpl<CertificateView> implements View.OnClickListener, IhTitleLayout.OnRightTextClickListener {
    private String type;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_message_bt:
                if (PhoneUtils.isMobileEnable(getView().getPhoneNum())) {
                    if (type.equals("phone_certificate")) {
                        getMsgCode(getView().getPhoneNum());
                    } else {
                        if (getView().getPhoneNum().equals(App.userInfo.getPhoneNumber())) {
                            getMsgCode(getView().getPhoneNum());
                        } else {
                            ToastUtil.showMessage(getContext(), "请输入已绑定的电话号码");
                        }
                    }

                } else {
                    ToastUtil.showMessage(getContext(), "请输入正确的电话号码");
                }
                break;
            case R.id.pic_positive_img:
                getView().goToIdCardFrontDiscriminate();
                break;
            case R.id.pic_reverse_img:
                getView().goToIdCardBackDiscriminate();

                break;
            case R.id.upload_pic_reverse_tv:
                getView().goToIdCardBackDiscriminate();

                break;
            case R.id.upload_certificate_pic_commit:
                if (PhoneUtils.isMobileEnable(getView().getPhoneNum())) {
                    if (!TextUtils.isEmpty(getView().getMessageCode())) {
                        if (getView().getUrlList().size() == 2) {
                            inspecteMsgCode();
                        } else {
                            ToastUtil.showMessage(getContext(), "证件图片上传未完成，请检查并完成上传");
                        }
                    } else {
                        ToastUtil.showMessage(getContext(), "请输入正确的验证码");
                    }

                } else {
                    ToastUtil.showMessage(getContext(), "请输入正确的电话号码");
                }
                break;
            case R.id.upload_pic_positive_tv:
                getView().goToIdCardFrontDiscriminate();

                break;
        }

    }

    @Override
    public void OnRightTextClick(View v) {
        if (type.equals("phone_certificate")) {
            if (PhoneUtils.isMobileEnable(getView().getPhoneNum())) {
                if (!TextUtils.isEmpty(getView().getMessageCode())) {
                    inspecteMsgCode();
                } else {
                    ToastUtil.showMessage(getContext(), "请输入正确的验证码");
                }
            } else {
                ToastUtil.showMessage(getContext(), "请输入正确的电话号码");
            }
        } else {
            inspecteIdCard();
        }
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 短信验证
     */
    private void inspecteMsgCode() {
        showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("phoneNumber", getView().getPhoneNum());
        map.put("verificationCode", getView().getMessageCode());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginService.class).verificationCode(map), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                getView().msgInspectSuccess();
                hideLoading();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().msgInspectFailed(msg);
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getMsgCode(String s) {
        showLoading();
        String type = getView().isPhoneCertificate() ? null : "2";
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginService.class).sendCode(s, type), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {

            @Override
            public void requestComplete(@Nullable Object data) {
                getView().getMsgCodeSuccess();
                hideLoading();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getMsgCodeFailed(msg);
                hideLoading();
                return super.requestError(exception, code, msg);

            }
        });
    }

    /**
     * 实名认证
     */
    public void inspecteIdCard() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", Global.getUserId());
        map.put("userName", getView().getName());
        map.put("idCard", getView().getIdCardNum());
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).realNameCertificate(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                getView().idCardCertificateSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().idCardCertificateFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 上传图片
     */
    public void upLoadPic(String path, final String type) {
        File file = new File(path);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UploadService.class).upload(body), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().uploadImgSuccess(data, type);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().uploadImgFailed(msg, type);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
