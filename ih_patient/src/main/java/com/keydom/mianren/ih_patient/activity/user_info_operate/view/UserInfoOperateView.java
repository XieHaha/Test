package com.keydom.mianren.ih_patient.activity.user_info_operate.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.PackageData;
import com.keydom.mianren.ih_patient.bean.UserInfo;

/**
 * 个人信息view
 */
public interface UserInfoOperateView extends BaseView {

    /**
     * 获取所在地
     */
    void getRegion(PackageData.ProvinceBean provinceBean, int position_city, int position_area);
    /**
     * 获取所在地
     */
    void getRegion(PackageData.ProvinceBean provinceBean,int position_city);
    /**
     * 获取所在地
     */
    void getRegion(PackageData.ProvinceBean provinceBean);

    /**
     * 设置性别
     */
    void setSex(String sexStr);

    /**
     * 设置民族
     */
    void setNation(String nationStr);

    /**
     * 设置姓名
     */
    void setName(String nameStr);

    /**
     * 设置国家
     */
    void setCountry(String countryStr);

    /**
     * 刷新个人数据
     */
    void initUserData(UserInfo data);

    /**
     * 重新加载
     */
    void reloadData();

    /**
     * 上传图片成功
     */
    void uploadImgSuccess(String urlData);

    /**
     * 上传失败
     */
    void uploadImgFailed(String errMsg);

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
    String getSex();
    String getNation();
    String getCountry();
}
