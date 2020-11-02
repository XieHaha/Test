package com.keydom.mianren.ih_doctor.activity.online_diagnose.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_common.bean.CheckOutGroupBean;
import com.keydom.ih_common.bean.InspectionApplyBean;
import com.keydom.mianren.ih_doctor.bean.OrderApplyResponse;

import java.util.Date;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface ApplyForCheckView extends BaseView {

    /**
     * 获取页面上的诊断信息
     */
    String getDiagnose();

    /**
     * 获取诊断
     *
     * @return 诊断内容
     */
    String getDisease();

    /**
     * 获取创建、修改检验订单参数
     */
    InspectionApplyBean getCheckoutParams();

    /**
     * 创建、修改检验订单成功
     */
    void saveTestOrderSuccess(List<OrderApplyResponse> bean);

    /**
     * 创建、修改检验订单失败
     */
    void saveTestOrderFailed(String errMsg);

    /**
     * 是否是检查单
     *
     * @return true 检查单
     */
    boolean isInspect();

    /**
     * 设置选中的检查项目列表
     *
     * @param list 检查项目列表
     */
    void getSelectInspectItemList(List<CheckOutGroupBean> list);

    /**
     * 是否是保存的检验单
     *
     * @return true 检验单
     */
    boolean isSaveCheckOutOrder();


    /**
     * 删除订单成功
     */
    void deleteOrderSuccess();

    /**
     * 删除订单失败
     *
     * @param errMsg 失败提示信息
     */
    void deleteOrderFailed(String errMsg);

    /**
     * 获取选中的检验项目列表
     *
     * @return 检验项目列表数据
     */
    List<CheckOutGroupBean> getCheckOutSelectItemList();

    void setMorbidityDate(Date date);
}