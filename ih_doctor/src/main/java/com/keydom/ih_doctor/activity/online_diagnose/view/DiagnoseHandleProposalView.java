package com.keydom.ih_doctor.activity.online_diagnose.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.DiagnoseHandleBean;

import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface DiagnoseHandleProposalView extends BaseView {

    /**
     * 获取提交处置建议请求参数
     *
     * @return
     */
    Map<String, Object> getHandleMap();

    /**
     * 开具处置建议成功
     *
     * @param msg 成功信息
     */
    void handleSuccess(DiagnoseHandleBean msg);

    /**
     * 开具处置建议失败
     *
     * @param errMsg 失败提示信息
     */
    void handleFailed(String errMsg);

    /**
     * 检查是否可以提交
     *
     * @return true 可以提交，false 不可以提交
     */
    boolean checkSubmit();
}