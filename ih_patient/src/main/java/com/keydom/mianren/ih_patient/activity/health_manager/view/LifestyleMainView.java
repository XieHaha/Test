package com.keydom.mianren.ih_patient.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.EatBean;
import com.keydom.mianren.ih_patient.bean.EatRecordBean;

import java.util.List;
import java.util.Map;

/**
 * @author 顿顿
 * @date 20/3/4 10:55
 * @des 生活记录
 */
public interface LifestyleMainView extends BaseView {

    void expandBreakfastLayout();

    void expandLunchLayout();

    void expandDinnerLayout();

    void expandExtraLayout();

    List<EatBean> getParams();

    boolean isNotToday();

    String getCurSelectDate();

    EatRecordBean getEatRecordBean();

    void requestFoodRecordSuccess(EatRecordBean bean);
    void requestFoodRecordFailed();

    void copyFoodRecordSuccess();

    Map<String, Object> getUpdateEatDataParams(EatRecordBean bean);

    void setNewDate(int value);

    String getPatientId();
}
