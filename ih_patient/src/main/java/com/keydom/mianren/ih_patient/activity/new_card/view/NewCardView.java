package com.keydom.mianren.ih_patient.activity.new_card.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.IdCardInfo;
import com.keydom.mianren.ih_patient.bean.PackageData;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 新卡view
 */
public interface NewCardView extends BaseView {

    /**
     * 提交成功
     */
    void commitSuccess();

    /**
     * 提交失败
     */
    void commitFailed(String msg);

    /**
     * 获取参数
     */
    Map<String,Object> getParams();

    /**
     * 获取卡类型
     */
    Map<String,Object> sendTypeCardParams();

    /**
     * 获取实名认证信息
     */
    Map<String,Object> getCertificateParams();

    /**
     * 获取其他证件类型
     */
    Map<String,Object> sendTypeOtherCerTificateParams();

    /**
     * 设置生日
     */
    void setBirthDate(Date date);

    /**
     * 设置卡有效期
     */
    void setIdCardValidityPeriodDate(Date date);

    /**
     * 保存时间
     */
    void saveRegion(List<PackageData.ProvinceBean> data, int options1, int option2, int options3);

    /**
     * 保存民族
     */
    void saveNation(PackageData.NationBean nationBean);

    /**
     * 设置性别
     */
    void setSex(String sexName,String sexCode);

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
    String getNation();
    String getBirthTime();
    String getValidityPeriod();

    /**
     * 匹配民族
     */
    void matchNation(PackageData.NationBean nationBean);


    /**
     * 获取ID信息成功
     */
    void getIdCardSuccess(IdCardInfo idCardInfo);

    /**
     * 获取ID信息失败
     */
    void getIdCardFailed(String msg);


    void finishMySelf();
}
