package com.keydom.mianren.ih_patient.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.EatRecordBean;

import java.util.Map;

/**
 * @author 顿顿
 * @date 20/3/4 10:55
 * @des 生活记录
 */
public interface LifestyleMainView extends BaseView {

    void requestFoodRecordSuccess(EatRecordBean bean);

    void updateFoodRecordSuccess(EatRecordBean bean);

    Map<String, Object> getUpdateEatDataParams(EatRecordBean bean);

    void setNewDate(int value);
}
