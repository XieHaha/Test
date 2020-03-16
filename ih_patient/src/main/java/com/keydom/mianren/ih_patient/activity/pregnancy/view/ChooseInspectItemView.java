package com.keydom.mianren.ih_patient.activity.pregnancy.view;


import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.CheckProjectsItem;

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
    List<CheckProjectsItem> getSelectItem();

}