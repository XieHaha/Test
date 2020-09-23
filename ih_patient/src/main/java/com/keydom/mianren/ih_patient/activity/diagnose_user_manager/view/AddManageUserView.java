package com.keydom.mianren.ih_patient.activity.diagnose_user_manager.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.bean.PackageData;

import java.util.Date;
import java.util.List;

/**
 * created date: 2018/12/13 on 15:03
 * des:添加就诊人view
 */
public interface AddManageUserView extends BaseView{

    /**
     * 设置性别
     */
    void setSex(String sex);

    /**
     * 设置生日
     */
    void setBirth(Date birth);

    /**
     * 获取就诊人实体
     */
    ManagerUserBean getManager();

    /**
     * 获取当前状态
     */
    int getStatus();

    void addOrEditSuccess(ManagerUserBean manager);

    /**
     * 设置时间区间
     */
    void saveRegion(List<PackageData.ProvinceBean> data, int options1, int option2, int options3);

    String getProvinceName();
    String getCityName();
    String getAreaName();
}
