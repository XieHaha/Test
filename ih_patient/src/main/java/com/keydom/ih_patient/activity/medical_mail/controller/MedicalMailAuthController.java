package com.keydom.ih_patient.activity.medical_mail.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.diagnose_user_manager.ManageUserSelectActivity;
import com.keydom.ih_patient.activity.medical_mail.view.MedicalMailAuthView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.MedicalMailApplyBean;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.net.UploadService;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 病案邮寄-身份认证
 */
public class MedicalMailAuthController extends ControllerImpl<MedicalMailAuthView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_select_visit:
                //                ManageUserActivity.start(getContext(), ManageUserActivity
                //                .FROMDIAGNOSES);
                ManageUserSelectActivity.start(getContext(), getView().getCurUserId(),
                        ManageUserSelectActivity.FROM_SELECT);
                break;
            case R.id.layout_front:
                getView().goToIdCardFrontDiscriminate();
                break;
            case R.id.layout_back:
                getView().goToIdCardBackDiscriminate();
                break;
            case R.id.layout_hand:
                getView().goToIdCardHandDiscriminate();
                break;
            case R.id.tv_next:
                onNext();
                break;
            default:
                break;
        }
    }

    private void onNext() {
        //        if (TextUtils.isEmpty(getView().getFrontUrl())) {
        //            ToastUtil.showMessage(getContext(), "患者身份证正面不能为空");
        //            return;
        //        }
        //        if (TextUtils.isEmpty(getView().getBackUrl())) {
        //            ToastUtil.showMessage(getContext(), "患者身份证反面不能为空");
        //            return;
        //        }
        //        if (TextUtils.isEmpty(getView().getHandUrl())) {
        //            ToastUtil.showMessage(getContext(), "患者手持身份证不能为空");
        //            return;
        //        }
        //        if (TextUtils.isEmpty(getView().getName())) {
        //            ToastUtil.showMessage(getContext(), "患者姓名不能为空");
        //            return;
        //        }
        //        if (TextUtils.isEmpty(getView().getIdCard())) {
        //            ToastUtil.showMessage(getContext(), "患者身份证号码不能为空");
        //            return;
        //        }
        //        if (TextUtils.isEmpty(getView().getPhone())) {
        //            ToastUtil.showMessage(getContext(), "患者联系方式不能为空");
        //            return;
        //        }

        MedicalMailApplyBean bean = getView().getApplyData();
        if (bean == null) {
            bean = new MedicalMailApplyBean();
        }
        bean.setFrontUrl(getView().getFrontUrl());
        bean.setBackUrl(getView().getBackUrl());
        bean.setHandUrl(getView().getHandUrl());
        bean.setPatientName(getView().getName());
        bean.setPatientIdCard(getView().getIdCard());
        bean.setPatientPhone(getView().getPhone());
        EventBus.getDefault().post(new Event(EventType.MEDICAL_STEP_ONE, bean));
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
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UploadService.class).upload(body), new HttpSubscriber<String>(getContext(), getDisposable(), true, false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().uploadImgSuccess(data, type);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().uploadImgFailed(msg, type);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
