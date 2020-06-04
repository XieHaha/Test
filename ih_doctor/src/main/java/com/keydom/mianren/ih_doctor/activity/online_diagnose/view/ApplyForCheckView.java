package com.keydom.mianren.ih_doctor.activity.online_diagnose.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_common.bean.CheckOutGroupBean;
import com.keydom.mianren.ih_doctor.bean.OrderApplyResponse;

import java.util.List;
import java.util.Map;

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
    String getMainDec();

    /**
     * 获取创建、修改检验订单参数
     */
    Map<String, Object> getTestMap();

    /**
     * 获取创建、修改检查订单参数
     */
    Map<String, Object> getInspectMap();


    /**
     * 创建、修改检验订单成功
     */
    void saveTestOrderSuccess(List<OrderApplyResponse> bean);

    /**
     * 创建、修改检验订单失败
     */
    void saveTestOrderFailed(String errMsg);

    /**
     * 创建、修改检查订单成功
     */
    void saveInspectOrderSuccess(List<OrderApplyResponse> bean);

    /**
     * 创建、修改检查订单失败
     */
    void saveInspectOrderFailed(String errMsg);

    /**
     * 获取检查项目成功
     *
     * @param list 检查项目数据
     */
    void getInspectItemListSuccess(List<CheckOutGroupBean> list);

    /**
     * 获取检查项目失败
     *
     * @param errMsg 失败信息
     */
    void getInspectItemListFailed(String errMsg);

    /**
     * 是否是检查单
     *
     * @return true 检查单
     */
    boolean isInspect();

    /**
     * 获取检查项目列表
     *
     * @return 检查项目列表
     */
    List<CheckOutGroupBean> getInspectItemList();

    /**
     * 获取选中的检查项目列表
     *
     * @return 检查项目列表
     */
    List<CheckOutGroupBean> getInspactSelectItemList();

    /**
     * 设置选中的检查项目列表
     *
     * @param list 检查项目列表
     */
    void getSelectInspectItemList(List<CheckOutGroupBean> list);

    /**
     * 是否是保存的检查单
     *
     * @return true 检查单
     */
    boolean isSaveInspectOrder();

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
     * 获取诊断
     *
     * @return 诊断内容
     */
    String getDisease();

    /**
     * 获取选中的检验项目列表
     *
     * @return 检验项目列表数据
     */
    List<CheckOutGroupBean> getCheckOutSelectItemList();
}