package com.keydom.ih_patient.activity.child_health.view;

import com.keydom.ih_common.base.BaseView;

/**
 * @date 20/2/27 11:38
 * @des 儿童保健首页view
 */
public interface ChildHealthView extends BaseView {
    /**
     * 儿童保健首页数据
     */
    void requestSuccess(String data);

    void transTitleBar(boolean direction, float scale);

    void finishPage();
}
