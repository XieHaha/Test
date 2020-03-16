package com.keydom.mianren.ih_patient.activity.function_config.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.IndexFunction;

import java.util.List;

/**
 * 菜单view
 */
public interface FunctionConfigView extends BaseView {
    /**
     * 修改编辑状态
     */
    void changEditStatus();

    /**
     * 填充菜单数据
     */
    void fillFunctionData(List<IndexFunction> allFunctionlist,List<IndexFunction> selectedFunctionlist);

    /**
     * 本地化配置
     */
    void localizationConfig();

    /**
     * 判断是否处于编辑状态
     */
    boolean isEditing();
}
