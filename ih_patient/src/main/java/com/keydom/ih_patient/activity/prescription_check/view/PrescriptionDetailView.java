package com.keydom.ih_patient.activity.prescription_check.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.PrescriptionDetailBean;

/**
 * created date: 2019/1/18 on 14:31
 * des:处方详情view
 */
public interface PrescriptionDetailView extends BaseView {
    /**
     * 获取详情成功回调
     */
    void getDetailSuccess(PrescriptionDetailBean bean);
}
