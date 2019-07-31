package com.keydom.ih_doctor.activity.online_diagnose.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.ICD10Bean;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface DiagnoseInputView extends BaseView {

    /**
     * 获取ICD－10列表数据成功
     *
     * @param list 数据
     */
    void getICDListSuccess(List<ICD10Bean> list);

    /**
     * 获取ICD－10失败
     *
     * @param errMsg 失败提示信息
     */
    void getICDListFailed(String errMsg);

    /**
     * 获取搜索关键字
     *
     * @return
     */
    String getKeyWord();

    /**
     * 清除列表
     */
    void clearList();
}