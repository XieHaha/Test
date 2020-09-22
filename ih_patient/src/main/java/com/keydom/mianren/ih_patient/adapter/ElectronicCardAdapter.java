package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;

import java.util.List;

/**
 * 电子健康卡
 *
 * @author 顿顿
 */
public class ElectronicCardAdapter extends BaseQuickAdapter<MedicalCardInfo, BaseViewHolder> {
    /**
     * 构造方法
     */
    public ElectronicCardAdapter(@Nullable List<MedicalCardInfo> checkoutResultList) {
        super(R.layout.item_electronic_card, checkoutResultList);
    }

    @Override
    protected void convert(BaseViewHolder helper, MedicalCardInfo item) {
        helper.setText(R.id.electronic_card_name, item.getName());
    }
}
