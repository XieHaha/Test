package com.keydom.ih_patient.activity.prescription_check.view;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.MedicalCardInfo;
import com.keydom.ih_patient.constant.TypeEnum;

import java.util.List;

/**
 * created date: 2019/1/17 on 20:03
 * des:电子处方view
 */
public interface PrescriptionView extends BaseView {
    /**
     * 显示选择就诊人弹框
     */
    void showCardPopupWindow();

    /**
     * 获取就诊卡列表
     */
    void fillDataList(List<MedicalCardInfo> dataList);

    /**
     * 获取处方列表
     */
    void listDataCallBack(List<MultiItemEntity> data, TypeEnum typeEnum);
}
