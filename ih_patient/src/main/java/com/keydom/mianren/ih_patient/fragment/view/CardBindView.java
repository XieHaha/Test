package com.keydom.mianren.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;

import java.util.Map;

/**
 * 绑卡view
 */
public interface CardBindView extends BaseView {

    /**
     * 绑卡成功
     */
    void bindSuccess();

    /**
     * 绑卡失败
     */
    void bindFailed(String errMsg);

    /**
     * 获取绑定数据
     */
    Map<String, Object> getBindMap();
}
