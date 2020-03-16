package com.keydom.mianren.ih_doctor.activity.view;

import com.keydom.ih_common.base.BaseView;

import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：通用编辑页面
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface CommonInputView extends BaseView {
    /**
     * 更新成功
     *
     * @param msg 成功信息
     */
    void updateSuccess(String msg);

    /**
     * 更新失败
     *
     * @param errMsg 失败信息
     */
    void updateFailed(String errMsg);

    /**
     * 获取更新参数，目前只有个人信息直接在本页面进行更新
     *
     * @return
     */
    Map<String, Object> getUpdatePersonalInfoMap();

}