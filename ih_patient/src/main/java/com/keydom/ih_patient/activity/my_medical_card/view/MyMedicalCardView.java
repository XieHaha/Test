package com.keydom.ih_patient.activity.my_medical_card.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.MedicalCardInfo;

import java.util.List;

/**
 * 我的就诊卡view
 */
public interface MyMedicalCardView extends BaseView {

    /**
     * 获取就诊卡数据列表
     */
    void fillDataList(List<MedicalCardInfo> dataList);


    /**
     * 获取失败
     */
    void fillDataListFailed(String errMsg);
}
