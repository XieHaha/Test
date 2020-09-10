package com.keydom.mianren.ih_patient.activity.medical_record.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.bean.MedicalRecordBean;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * created date: 2019/1/4 on 13:04
 * des:电子处方view
 *
 * @author 顿顿
 */
public interface MedicalRecordView extends BaseView {
    /**
     * 获取门诊病历记录回调
     */
    void getRecordList(List<MedicalRecordBean> list);

    Map<String,Object> getParams();

    void setStartDate(Date date);

    void setEndDate(Date date);

    MedicalCardInfo getMedicalCardInfo();
}
