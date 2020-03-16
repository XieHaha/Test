package com.keydom.mianren.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.PhysicalExaCommentsInfo;

import java.util.List;

/**
 * 体检套餐view
 */
public interface PhysicalExaCommentsView extends BaseView {

    /**
     * 获取套餐成功
     */
    void updateCommentsListSuccess(List<PhysicalExaCommentsInfo> dataList);

    /**
     * 获取失败
     */
    void updateCommentsListFailed(String errMsg);
}
