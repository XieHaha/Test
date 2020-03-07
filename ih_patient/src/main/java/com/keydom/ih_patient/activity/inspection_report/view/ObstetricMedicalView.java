package com.keydom.ih_patient.activity.inspection_report.view;

import com.keydom.ih_common.base.BaseView;

/**
 * @date 20/3/7 14:00
 * @des 产科病历首页
 */
public interface ObstetricMedicalView extends BaseView {
    /**
     * 更新门诊数据（第几次）
     */
    void updateOutpatientDate(int position);
}
