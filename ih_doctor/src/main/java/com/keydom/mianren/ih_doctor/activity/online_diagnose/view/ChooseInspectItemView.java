package com.keydom.mianren.ih_doctor.activity.online_diagnose.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.CheckOutItemBean;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface ChooseInspectItemView extends BaseView {

    /**
     * 获取选中项目列表
     *
     * @return
     */
    List<CheckOutItemBean> getSelectItem();

}