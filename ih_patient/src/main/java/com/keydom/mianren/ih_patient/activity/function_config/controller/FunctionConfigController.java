package com.keydom.mianren.ih_patient.activity.function_config.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.function_config.FunctionConfigActivity;
import com.keydom.mianren.ih_patient.activity.function_config.view.FunctionConfigView;
import com.keydom.mianren.ih_patient.bean.IndexFunction;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.utils.LocalizationUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单配置控制器
 */
public class FunctionConfigController extends ControllerImpl<FunctionConfigView> implements View.OnClickListener, IhTitleLayout.OnRightTextClickListener {
    private List<IndexFunction> indexFunctionList;

    /**
     * 获取本地菜单配置状态
     */
    public void initData() {
        String filename = "index_function_" + Global.getUserId();
        String allFunctionFilename = "all_function_" + Global.getUserId();
        List<IndexFunction> selectedFunctionlist =
                (List<IndexFunction>) LocalizationUtils.readFileFromLocal(getContext(), filename);

        List<IndexFunction> allFunctionlist =
                (List<IndexFunction>) LocalizationUtils.readFileFromLocal(getContext(),
                        allFunctionFilename);
        //TODO 临时添加非会员 住院预缴金  2020年3月9日 15:16:52
        allFunctionlist.add(new IndexFunction(R.mipmap.icon_hospital_payment, 34, "住院预缴"));
        if (allFunctionlist != null) {
            Logger.e("selectedFunctionlist.size==" + allFunctionlist.size());
        } else {
            Logger.e("selectedFunctionlist==null");
        }
        if (selectedFunctionlist != null) {
            Logger.e("selectedFunctionlist.size==" + selectedFunctionlist.size());
        } else {
            Logger.e("selectedFunctionlist==null");
            selectedFunctionlist = new ArrayList<>();
            if (allFunctionlist != null) {
                if (allFunctionlist.size() > 7) {
                    selectedFunctionlist.addAll(allFunctionlist.subList(0, 6));
                } else {
                    selectedFunctionlist.addAll(allFunctionlist);
                }
            }

        }
        if (selectedFunctionlist != null && allFunctionlist != null) {
            getView().fillFunctionData(allFunctionlist, selectedFunctionlist);
        } else
            ToastUtil.showMessage(getContext(), "获取菜单配置失败");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.function_config_editor_tv:
                getView().changEditStatus();
                break;
        }
    }

    @Override
    public void OnRightTextClick(View v) {
        if (getView().isEditing()) {
            getView().changEditStatus();
            getView().localizationConfig();
        }
        ((FunctionConfigActivity) getContext()).finish();
    }
}
