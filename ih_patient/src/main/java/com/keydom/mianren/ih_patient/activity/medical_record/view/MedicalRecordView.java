package com.keydom.mianren.ih_patient.activity.medical_record.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;

import java.util.List;

/**
 * created date: 2019/1/4 on 13:04
 * des:电子处方view
 *
 * @author 顿顿
 */
public interface MedicalRecordView extends BaseView {
    void getAllCardSuccess(List<MedicalCardInfo> data);
}
