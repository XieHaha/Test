package com.keydom.mianren.ih_patient.activity.child_health.view;

import com.keydom.ih_common.base.BaseView;

import java.util.Date;
import java.util.Map;

/**
 * @author 顿顿
 * @date 20/2/27 11:38
 * @des 儿童保健预约详情view
 */
public interface ChildHealthDetailView extends BaseView {

    void applySuccess();

    void setReserveDate(Date date);

    boolean commitAble();

    Map<String, Object> getParams();
}
