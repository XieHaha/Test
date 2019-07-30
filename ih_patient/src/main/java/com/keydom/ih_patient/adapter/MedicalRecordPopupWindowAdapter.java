package com.keydom.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.MedicalCardInfo;

import java.util.List;

/**
 * created date: 2019/1/4 on 14:43
 * des:处方记录就诊卡适配器
 */
public class MedicalRecordPopupWindowAdapter extends BaseQuickAdapter<MedicalCardInfo,BaseViewHolder> {
    public MedicalRecordPopupWindowAdapter(@Nullable List<MedicalCardInfo> data) {
        super(R.layout.exa_report_card_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MedicalCardInfo item) {
        helper.setText(R.id.card_user_name_tv,item.getName());
    }
}
