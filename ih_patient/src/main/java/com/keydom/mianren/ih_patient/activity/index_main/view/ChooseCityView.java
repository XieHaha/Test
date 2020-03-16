package com.keydom.mianren.ih_patient.activity.index_main.view;

import com.keydom.ih_common.base.BaseView;

import java.util.List;

/**
 * 城市选择view
 */
public interface ChooseCityView extends BaseView {
    /**
     * 获取城市列表接口回调
     */
    void getCityListSuccess(List<Object> dataList);

    /**
     * 获取失败
     */
    void getCityListFailed(String errMsg);

    /**
     * 获取关键字
     */
    String getSearchKeyWord();
}
