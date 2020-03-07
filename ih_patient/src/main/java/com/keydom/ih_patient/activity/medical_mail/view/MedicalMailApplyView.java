package com.keydom.ih_patient.activity.medical_mail.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.MedicalMailApplyBean;

/**
 * @date 20/3/2 15:55
 * @des 病案邮寄-邮寄申请
 */
public interface MedicalMailApplyView extends BaseView {
    String getName();

    String getIdCard();

    String getLeaveTime();

    String getWardNumber();

    String getInHospitalNumber();

    String getCopyPurpose();

    String getCopyQuantity();

    String getCopyContent();

    MedicalMailApplyBean getApplyData();
}
