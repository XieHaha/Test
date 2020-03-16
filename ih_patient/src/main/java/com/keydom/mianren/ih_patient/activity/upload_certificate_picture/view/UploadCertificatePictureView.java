package com.keydom.mianren.ih_patient.activity.upload_certificate_picture.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.IdCardBean;

import java.util.ArrayList;

/**
 * 身份认证view
 */
public interface UploadCertificatePictureView extends BaseView {
    /**
     * 关闭页面
     */
    void finishActivity();

    /**
     * 上传图片
     */
    void sendPicUrl();

    /**
     * 上传图片成功
     */
    void uploadImgSuccess(String data, String type);

    /**
     * 上传失败
     */
    void uploadImgFailed(String msg,String type);

    /**
     * 获取图片返回url
     */
    ArrayList<String> getUrlList();

    void goToIdCardFrontDiscriminate();

    void goToIdCardBackDiscriminate();

    IdCardBean getResult();
}
