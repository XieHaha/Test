package com.keydom.mianren.ih_doctor.activity.nurse_service.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.NurseBean;

import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface SelectNurseView extends BaseView {


    /**
     * 获取护士查询参数
     *
     * @return 查询参数
     */
    Map<String, Object> getQueryMap();

    /**
     * 获取护士列表成功
     *
     * @param list 护士列表
     */
    void getNurseListSuccess(List<NurseBean> list);

    /**
     * 获取护士列表失败
     *
     * @param errMsg 失败信息
     */
    void getNurseListFailed(String errMsg);


}