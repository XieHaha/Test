package com.keydom.ih_patient.activity.order_hospital_cure.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.HospitalCureInfo;

import java.util.Map;

/**
 * 入院订单view
 */
public interface OrderHospitalCureView extends BaseView {

    /**
     * 获取订单
     */
    void getOrderRegion(String regionName,String regionCode);

    /**
     * 获取健康入院证
     */
    void getHealthCareRegion(String regionName,String regionCode);

    /**
     * 获取健康类型
     */
    void getHealthType(int healthTypeCode);


    /**
     * 获取异地类型
     */
    void getSettlementType(int SettlementTypeCode);

    /**
     * 申请入院成功
     */
    void applyHealthCureSuccess();

    /**
     * 申请失败
     */
    void applyHealthCureFailed(String errMsg);

    /**
     * 获取入院成功
     */
    void getHealthCureSuccess(HospitalCureInfo hospitalCureInfo);

    /**
     * 获取失败
     */
    void getHealthCureFailed(String errMsg);

    /**
     * 更新状态成功
     */
    void updateStatusSuccess();

    /**
     * 更新状态失败
     */
    void updateStatusFailed(String errMsg);

    /**
     * 取消申请成功
     */
    void cancellationApplySuccess();

    /**
     * 取消失败
     */
    void cancellationApplyFailed(String msg);

    /**
     * 获取准许编号
     */
    String getAdmissionNumber();

    /**
     * 获取申请参数
     */
    Map<String,Object> getApplyMap();
}
