package com.keydom.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.CityBean;
import com.keydom.ih_patient.bean.HospitalAreaInfo;
import com.keydom.ih_patient.bean.IndexData;
import com.keydom.ih_patient.bean.IndexFunction;

import java.util.List;

/**
 * 主页index view
 */
public interface TabIndexView extends BaseView {

    /**
     * 数据请求失败
     */
    void dataRequestFailed(String msg);

    /**
     * 设置banner
     */
    void setPicBannerData(List<IndexData.HeaderbannerBean> urlList);

    /**
     * 设置功能列表
     */
    void setFunctionRvData(List<IndexFunction> dataList);

    /**
     * 设置banner
     */
    void setAdBannerData(List<IndexData.AdvertisementBean> urlList);

    /**
     * 设置通知信息
     */
    void setNoticeData(List<IndexData.NotificationsBean> StringList);

    /**
     * 设置文章
     */
    void setArticleData(List<IndexData.HealthKnowledgesBean> dataList);

    /**
     * 获取用户id
     */
    String getUserId();

    /**
     * 获取医院code
     */
    String getHospitalCode();

    /**
     * 获取医院成功
     */
    void getHospitalListSuccess(List<HospitalAreaInfo> dataList);

    /**
     * 获取医院失败
     */
    void getHospitalListFailed(String errMsg);

    /**
     * 展示选择医院弹框
     */
    void showHospitalPopupWindow();

    List<IndexData.NotificationsBean> getNoticeList();

    void getCityListSuccess(List<Object> data);

    void getCityListFailed(String msg);
}
