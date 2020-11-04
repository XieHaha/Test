package com.keydom.mianren.ih_patient.activity.online_diagnoses_order.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_common.bean.DoctorInfo;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.bean.PayOrderBean;

import java.util.List;
import java.util.Map;

/**
 * 问诊订单填写view
 */
public interface DiagnosesApplyView extends BaseView {

    String getPatientInputValue();

    /**
     * 获取患者成功
     */
    void getPatientListSuccess(List<ManagerUserBean> data);

    /**
     * 获取患者失败
     */
    void getPatientListFailed(String errMsg);

    void getReceptionDoctorSuccess(DoctorInfo doctorInfo);

    /**
     * 获取图片size
     */
    int getImgSize();

    /**
     * 获取最后一次点击项目
     */
    boolean getLastItemClick(int position);

    /**
     * 上传图片成功
     */
    void uploadSuccess(String msg);

    /**
     * 上传图片失败
     */
    void uploadFailed(String msg);

    /**
     * 获取请求map
     */
    Map<String,Object> getQueryMap();


    /**
     * 获取订单信息
     */
    void getOrderInfo(PayOrderBean data);

    /**
     * 获取订单失败
     */
    void getOrderInfoFailed(String msg);

    /**
     * 提交问诊失败
     */
    void applyDiagnosesFailed(String errMsg);

    /**
     * 判断卡是否更换
     */
    boolean isHavePatient();

    ManagerUserBean getPatient();

    /**
     * 获取支付描述
     */
    String getPayDesc();

    String getPicUrl(int position);

    List<String> getPicList();

    int getType();
}
