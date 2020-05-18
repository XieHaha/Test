package com.keydom.mianren.ih_doctor.activity.online_triage.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_common.bean.DiagnoseOrderDetailBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;

import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface TriageOrderDetailView extends BaseView {

    /**
     * 获取问诊详情
     */
    void getInquisitionDetailSuccess(DiagnoseOrderDetailBean bean);

    void getInquisitionDetailFailed(String msg);

    /**
     * 获取操作参数
     *
     * @param option 转诊单ID
     * @return 请求参数
     */
    Map<String, Object> getOperateMap(long option);

    /**
     * 操作成功
     *
     * @param successMsg 操作成功信息
     */
    void operationSuccess(String successMsg);

    /**
     * 操作失败
     *
     * @param errMsg 失败信息
     */
    void operationFailed(String errMsg);

    /**
     * 获取退回备注
     *
     * @return 备注
     */
    String getDialogValue();

    TypeEnum getOrderType();

    /**
     * 显示填写原因弹窗
     */
    void showDialog();

    /**
     * 隐藏填写原因弹窗
     */
    void hideDialog();

}