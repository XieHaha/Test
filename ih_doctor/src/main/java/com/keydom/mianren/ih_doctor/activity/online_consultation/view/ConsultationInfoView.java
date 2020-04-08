package com.keydom.mianren.ih_doctor.activity.online_consultation.view;

import com.keydom.ih_common.base.BaseView;

/**
 * @date 20/4/8 14:39
 * @des 会诊室-病历资料
 */
public interface ConsultationInfoView extends BaseView {
    /**
     * 会诊申请单
     */
    void onApplyInfoSelect();

    /**
     * 病历
     */
    void onMedicalSelect();

    /**
     * 报告单
     */
    void onReportInfoSelect();

    /**
     * 影像
     */
    void onVideoSelect();
}
