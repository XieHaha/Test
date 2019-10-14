package com.keydom.ih_patient.activity.im;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_patient.bean.InquiryBean;
import com.keydom.ih_patient.bean.LocationInfo;

import java.util.List;

/**
 * 问诊view
 */
interface ConversationView extends BaseView {

    String getUserId();

    /**
     * 问诊单id
     *
     * @return 问诊单id
     */
    long getId();

    /**
     * 医生id
     *
     * @return 医生id
     */
    String getDoctorCode();

    /**
     * 是否取消结束问诊
     *
     * @return -1 取消 1确认
     */
    int getEndType();

    /**
     * 转诊订单id
     *
     * @return 转诊订单id
     */
    long getReferralId();

    /**
     * 是否同意转诊
     *
     * @return 1接受 -1 拒绝
     */
    int getReferralState();

    /**
     * 需要查询的是否已支付订单id
     *
     * @return
     */
    long getIsPayId();

    /**
     * 加载问诊状态成功
     *
     * @param data
     */
    void loadSuccess(InquiryBean data);

    /**
     * 处理医生申请结束问诊成功
     */
    void handleApplyEndSuccess();

    /**
     * 发起结束问诊成功
     */
    void endSuccess();

    /**
     * 处理是否接受转诊成功
     */
    void operateReferralSuccess(String id);

    /**
     * 支付成功
     */
    void paySuccess();

    /**
     * 是否支付回调
     *
     * @param isPay true已支付 false未支付
     */
    void payType(boolean isPay,boolean isWaiYan);

    /**
     * 退诊成功
     */
    void returnBackSuccess();

    /**
     * 退诊失败
     *
     * @param msg
     */
    void returnBackFailed(String msg);

    /**
     * 是否同意换诊
     *
     * @return 1接受 -1 拒绝
     */
    int getChangeDoctor();

    /**
     * 操作换诊成功
     */
    void changeDoctorSuccess();

    void getDistributionFee(String data);
    void getLocationList(List<LocationInfo> data);
}
