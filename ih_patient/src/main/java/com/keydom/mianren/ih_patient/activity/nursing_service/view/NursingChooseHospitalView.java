package com.keydom.mianren.ih_patient.activity.nursing_service.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.BaseNurseFeeBean;
import com.keydom.mianren.ih_patient.bean.HospitalLocationInfo;
import com.keydom.mianren.ih_patient.bean.LocationInfo;

import java.util.List;

/**
 * 护理服务选择医院view
 */
public interface NursingChooseHospitalView extends BaseView {

    /**
     * 获取医院位置信息回调
     */
    void getHospitalLocationInfoSuccess(List<HospitalLocationInfo> hospitalLocationInfoList);

    /**
     * 获取失败
     */
    void getHospitalLocationFailed(String errMsg);

    /**
     * 获取默认地址
     */
    void getDefaultAddressSuccess(List<LocationInfo> data);

    /**
     * 获取失败
     */
    void getDefaultAddressFailed(String errMsg);

    /**
     * 获取基础费用
     */
    void getBaseFee(BaseNurseFeeBean baseNurseFeeBean);

    /**
     * 获取基础费用失败
     */
    void getBaseFeeFailed(String errMsg);
}
