package com.keydom.mianren.ih_doctor.activity.online_diagnose.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.CheckOutGroupBean;
import com.keydom.mianren.ih_doctor.bean.CheckOutSubBean;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface DiagnoseCheckSelectItemView extends BaseView {

    /**
     * 获取检验项目成功
     *
     * @param list 检验项目列表
     */
    void getGroupListSuccess(List<CheckOutGroupBean> list);

    /**
     * 获取检验项目失败
     *
     * @param errMsg 失败提示信息
     */
    void getGroupListFailed(String errMsg);

    /**
     * 获取检验项目成功
     *
     * @param list 检验项目列表
     */
    void getItemListSuccess(List<CheckOutSubBean> list);

    /**
     * 获取检验项目失败
     *
     * @param errMsg 失败提示信息
     */
    void getItemListFailed(String errMsg);
}