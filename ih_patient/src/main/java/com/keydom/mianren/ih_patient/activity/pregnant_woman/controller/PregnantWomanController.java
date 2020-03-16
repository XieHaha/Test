package com.keydom.mianren.ih_patient.activity.pregnant_woman.controller;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.diagnose_main.DiagnoseMainActivity;
import com.keydom.mianren.ih_patient.activity.pregnant_woman.view.PregnantWomanView;
import com.keydom.mianren.ih_patient.constant.TypeEnum;

/**
 * @date 20/2/28 15:15
 * @des 孕妇学校
 */
public class PregnantWomanController extends ControllerImpl<PregnantWomanView> implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_online:
                DiagnoseMainActivity.start(getContext());
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    /**
     * 文章信息
     */
    public void getArticleData(TypeEnum typeEnum) {
        getView().bindData();
    }
}
