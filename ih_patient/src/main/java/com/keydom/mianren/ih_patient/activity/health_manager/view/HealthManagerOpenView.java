package com.keydom.mianren.ih_patient.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;

/**
 * @author 顿顿
 * @date 20/3/4 10:55
 * @des 健康管理
 */
public interface HealthManagerOpenView extends BaseView {
    boolean isSelected();

    void setSelect();

    void openHealthManagerSuccess();
}
