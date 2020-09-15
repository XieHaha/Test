package com.keydom.mianren.ih_patient.activity.pregnancy.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.bean.PregnancyRecordBean;

import java.util.List;

/**
 * created date: 2019/1/4 on 13:04
 * des:电子处方view
 *
 * @author 顿顿
 */
public interface PregnancyRecordView extends BaseView {
    /**
     * 获取门诊病历记录回调
     */
    void requestRecordSuccess(List<PregnancyRecordBean> list);

    MedicalCardInfo getMedicalCardInfo();
}
