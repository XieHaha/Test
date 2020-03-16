package com.keydom.mianren.ih_patient.activity.location_manage.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.PackageData;

import java.util.List;
import java.util.Map;

/**
 * 添加地址view
 */
public interface AddLocationView extends BaseView {

    /**
     * 获取type
     */
    String getType();

    /**
     * 获取添加数据
     */
    Map<String,Object> getAddMap();

    /**
     * 获取编辑数据
     */
    Map<String,Object> getEditMap();

    /**
     * 添加成功
     */
    void addSuccess();

    /**
     * 添加失败
     */
    void addFailed(String errMsg);

    /**
     * 编辑成功
     */
    void editSuccess();

    /**
     * 编辑失败
     */
    void editFailed(String errMsg);

    /**
     * 保存时间
     */
    void saveRegion(List<PackageData.ProvinceBean> data,int options1, int option2, int options3);

    /**
     * 获取省份
     */
    String getProvinceName();

    /**
     * 获取城市
     */
    String getCityName();

    /**
     * 获取地区
     */
    String getAreaName();
}
