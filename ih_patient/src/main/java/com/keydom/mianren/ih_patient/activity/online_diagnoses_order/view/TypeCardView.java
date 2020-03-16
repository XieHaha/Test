package com.keydom.mianren.ih_patient.activity.online_diagnoses_order.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;

import java.util.List;

/**
 * 卡类型view
 */
public interface TypeCardView extends BaseView {
    /**
     * 查询就诊卡列表接口回调
     */
    void getAllCardSuccess(List<MedicalCardInfo> dataList);

    /**
     * 查询就诊卡失败
     */
    void getAllCardFailed(String errMsg);
}
