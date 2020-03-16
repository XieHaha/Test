package com.keydom.mianren.ih_patient.activity.reserve_examination.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.constant.TypeEnum;

import java.util.List;

/**
 * 检验检查预约列表view
 */
public interface ExaminationReserveRecordView extends BaseView {

    /**
     * 获取记录成功
     */
    void requestSuccess(List<String> dataList, TypeEnum typeEnum);

    /**
     * 获取记录失败
     */
    void requestFailed(String errMsg);
}
