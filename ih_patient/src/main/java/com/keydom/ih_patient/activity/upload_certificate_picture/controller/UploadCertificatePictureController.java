package com.keydom.ih_patient.activity.upload_certificate_picture.controller;

import android.app.Activity;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.new_card.NewCardActivity;
import com.keydom.ih_patient.activity.upload_certificate_picture.view.UploadCertificatePictureView;
import com.keydom.ih_patient.constant.Const;
import com.keydom.ih_patient.net.UploadService;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 身份认证控制器
 */
public class UploadCertificatePictureController extends ControllerImpl<UploadCertificatePictureView> implements View.OnClickListener {
    private String type;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upload_certificate_pic_commit:
                switch (type) {
                    case "card_id_card":
                        if (getView().getUrlList().size() == 2) {
                            NewCardActivity.start(getContext(), Const.CARD_ID_CARD, getView().getUrlList(),getView().getResult());
                        } else {
                            ToastUtil.showMessage(getContext(), "证件图片上传未完成，请检查并完成上传");
                        }
                        break;
                    case "card_other_certificate":
                        if (getView().getUrlList().size() == 2) {
                            NewCardActivity.start(getContext(), Const.CARD_OTHER_CERTIFICATE, getView().getUrlList(),null);
                        } else {
                            ToastUtil.showMessage(getContext(), "证件图片上传未完成，请检查并完成上传");
                        }

                        break;
                    case "user_info":
                        getView().sendPicUrl();
                        break;

                }
                break;
            case R.id.upload_pic_positive_tv:
                if ("card_id_card".equals(type)) {
                    getView().goToIdCardFrontDiscriminate();
                } else
                    PictureSelector.create((Activity) getContext())
                            .openGallery(PictureMimeType.ofImage())
                            .maxSelectNum(1)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.upload_pic_reverse_tv:
                if ("card_id_card".equals(type)) {
                    getView().goToIdCardBackDiscriminate();
                } else
                    PictureSelector.create((Activity) getContext())
                            .openGallery(PictureMimeType.ofImage())
                            .maxSelectNum(1)
                            .forResult(PictureConfig.REQUEST_CAMERA);
                break;
            case R.id.pic_positive_img:
                if ("card_id_card".equals(type)) {
                    getView().goToIdCardFrontDiscriminate();
                } else
                    PictureSelector.create((Activity) getContext())
                            .openGallery(PictureMimeType.ofImage())
                            .maxSelectNum(1)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.pic_reverse_img:
                if ("card_id_card".equals(type)) {
                    getView().goToIdCardBackDiscriminate();
                } else
                    PictureSelector.create((Activity) getContext())
                            .openGallery(PictureMimeType.ofImage())
                            .maxSelectNum(1)
                            .forResult(PictureConfig.REQUEST_CAMERA);
                break;
        }
    }

    /**
     * 获取认证类型
     */
    public void getType(String type) {
        this.type = type;
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
