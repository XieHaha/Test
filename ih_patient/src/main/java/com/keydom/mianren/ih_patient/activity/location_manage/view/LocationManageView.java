package com.keydom.mianren.ih_patient.activity.location_manage.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.LocationInfo;

import java.util.List;

/**
 * 地址管理view
 */
public interface LocationManageView extends BaseView {
    /**
     * 获取地址列表成功
     */
    void getLocationList(List<LocationInfo> dataList);

    /**
     * 获取失败
     */
    void getLocationListFailed(String errMsg);
}
