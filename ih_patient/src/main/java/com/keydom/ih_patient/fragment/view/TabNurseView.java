package com.keydom.ih_patient.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.BannerBean;
import com.keydom.ih_patient.bean.NursingIndexInfo;

import java.util.List;

/**
 * 护理服务view
 */
public interface TabNurseView extends BaseView {

    /**
     * 获取主页功能成功
     */
    void getIndexSuccess(NursingIndexInfo data);

    /**
     * 获取功能失败
     */
    void getIndexFailed(String errMsg);

    /**
     * 获取项目类型
     */
    long getProjectTypeId(String keyName);

    /**
     * 获取项目id
     */
    long getProjectId(int option);

    /**
     * 获取项目名称
     */
    String getProjectName(int option);

    /**
     * 展示医院选择弹框
     */
    void showHospitalPopupWindow();

    /**
     * 获取图片成功
     */
    void  getPicListSuccess(List<BannerBean> dataList);

    /**
     * 获取图片失败
     */
    void getPicListFailed(String errMsg);
    /*
    *获取未支付跳转位置
    * */
    int getUnpaytag();
}
