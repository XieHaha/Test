package com.keydom.ih_patient.activity.medical_mail.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.constant.TypeEnum;

import java.util.List;

/**
 * 病案邮寄view
 */
public interface MedicalMailOrderView extends BaseView {

    /**
     * 已邮寄 成功
     */
    void getMedicalMailedSuccess(List<String> dataList, TypeEnum typeEnum);

    /**
     * 已邮寄 失败
     */
    void getMedicalMailedFailed(String errMsg);

    /**
     * 未邮寄 成功
     */
    void getMedicalNotMailedSuccess(List<String> dataList, TypeEnum typeEnum);

    /**
     * 未邮寄 失败
     */
    void getMedicalNotMailedFailed(String errMsg);
}
