package com.keydom.mianren.ih_doctor.activity.online_consultation.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_common.bean.DiagnoseOrderDetailBean;
import com.keydom.mianren.ih_doctor.bean.ConsultationDetailBean;

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

    void requestInfoSuccess(ConsultationDetailBean bean);

    void requestInfoFailed(String error);

    void requestInquisitionSuccess(DiagnoseOrderDetailBean bean);

    void requestInquisitionFailed(String error);
}
