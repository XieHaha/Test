package com.keydom.mianren.ih_patient.activity.medical_record.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.bean.MedicalRecordBean;
import com.keydom.mianren.ih_patient.constant.TypeEnum;

import java.util.List;

/**
 * created date: 2019/1/4 on 13:04
 * des:电子处方view
 */
public interface MedicalRecordView extends BaseView {
    /**
     * 显示选择就诊卡弹框
     */
    void showCardPopupWindow();

    /**
     * 获取就诊卡回调
     */
    void fillDataList(List<MedicalCardInfo> dataList);

    /**
     * 获取电子处方记录回调
     */
    void getRecordList(List<MedicalRecordBean> list , TypeEnum typeEnum);
}
