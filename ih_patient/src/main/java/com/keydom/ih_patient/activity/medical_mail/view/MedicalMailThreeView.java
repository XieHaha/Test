package com.keydom.ih_patient.activity.medical_mail.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.MedicalMailApplyBean;
import com.keydom.ih_patient.bean.PackageData;

import java.util.List;

/**
 * @date 20/3/2 15:55
 * @des 病案邮寄-收件信息
 */
public interface MedicalMailThreeView extends BaseView {
    void saveRegion(List<PackageData.ProvinceBean> data, int options1, int option2, int options3);

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

    String getRecvName();

    String getRecvPhone();

    String getRecvCity();

    String getRecvCityDetail();

    MedicalMailApplyBean getApplyData();
}
