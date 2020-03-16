package com.keydom.mianren.ih_patient.activity.medical_record.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.MedicalRecordBean;

/**
 * created date: 2019/1/4 on 16:48
 * des:电子处方详情view
 */
public interface MedicalRecordDetailView extends BaseView{
    /**
     * 获取详情成功回调
     */
    void getDetailCallBack(MedicalRecordBean bean);
}
