package com.keydom.ih_patient.activity.medical_mail.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.MedicalMailApplyBean;

/**
 * @date 20/3/2 15:55
 * @des 病案邮寄-身份认证
 */
public interface MedicalMailOneView extends BaseView {

    String getFrontUrl();

    String getBackUrl();

    String getHandUrl();

    String getName();

    String getIdCard();

    String getPhone();

    void goToIdCardFrontDiscriminate();

    void goToIdCardBackDiscriminate();

    void goToIdCardHandDiscriminate();

    /**
     * 上传图片成功
     */
    void uploadImgSuccess(String data, String type);

    /**
     * 上传失败
     */
    void uploadImgFailed(String msg, String type);

    MedicalMailApplyBean getApplyData();

}
