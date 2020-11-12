package com.keydom.mianren.ih_patient.activity.reserve_painless_delivery.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;

import java.util.Date;
import java.util.List;

/**
 * @auth 顿顿
 * @date 20/2/24 15:44
 * @des
 */
public interface ReservePainlessDeliveryView extends BaseView {

    int getFetusValue();

    void setSelect();

    boolean isSelect();

    void setMenstruation(Date menstruation);

    void setDueDate(Date dueDate);
    void setReserveDate(Date reserveDate);
    void setFetus(int position);

    /**
     * 预约成功
     */
    void reserveSuccess();

    void reserveFailed();

    List<String> getFetusDit();

    /**
     * 获取就诊人
     */
    MedicalCardInfo getVisitUser();

    /**
     * 获取年龄
     */
    String getAge();

    /**
     * 末次月经
     */
    String getLastDate();

    /**
     * 预产期
     */
    String getDueDate();

    String getReserveDate();

    /**
     * 胎数
     */
    String getFetus();

    /**
     * 电话
     */
    String getPhone();

    /**
     * 查询就诊卡列表接口回调
     */
    void getAllCardSuccess(List<MedicalCardInfo> dataList);

    /**
     * 查询就诊卡失败
     */
    void getAllCardFailed(String errMsg);

}
