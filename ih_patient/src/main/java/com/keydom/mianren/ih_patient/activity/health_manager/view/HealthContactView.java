package com.keydom.mianren.ih_patient.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;

import java.util.List;

/**
 * @author 顿顿
 * @date 20/3/4 10:55
 * @des 紧急联系人
 */
public interface HealthContactView extends BaseView {

    List<String> getRelationshipData();
    void setRelationship(int position);
}
