package com.keydom.ih_patient.activity.reserve_amniocentesis.controller;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisRecordView;
import com.keydom.ih_patient.constant.TypeEnum;

/**
 * @date 20/3/11 14:26
 * @des 羊水穿刺预约查询及取消
 */
public class AmniocentesisRecordController extends ControllerImpl<AmniocentesisRecordView> implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    /**
     * 获取穿刺预约记录
     */
    public void getAmniocentesisRecord(TypeEnum typeEnum) {
        getView().onAmniocentesisRecordSuccess();
    }

    private void cancelAmniocentesisReserve() {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        cancelAmniocentesisReserve();
    }
}
