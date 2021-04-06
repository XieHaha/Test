package com.keydom.mianren.ih_patient.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.EatBean;

import java.util.List;

/**
 * @author 顿顿
 * @date 20/3/4 10:55
 * @des 生活数据
 */
public interface LifestyleDataView extends BaseView {
    List<EatBean> getParams();

    void updateFoodRecordSuccess();

    List<EatBean> getSelectEatBeans();
}
