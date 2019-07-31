package com.keydom.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;

/**
 * 体检套餐评论
 */
public interface PhysicalExaProcessView extends BaseView {

    /**
     * 获取评论成功
     */
    void FillPhysicalProcess(String data);

    /**
     * 获取失败
     */
    void FillPhysicalProcessFailed(String errMsg);
}
