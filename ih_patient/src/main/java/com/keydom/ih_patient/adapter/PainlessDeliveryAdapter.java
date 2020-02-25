package com.keydom.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_patient.bean.PainlessDeliveryInfo;

import java.util.ArrayList;

/**
 * @date 20/2/25 14:05
 * @des 无痛分娩预约适配器
 */
public class PainlessDeliveryAdapter extends BaseQuickAdapter<PainlessDeliveryInfo,
        BaseViewHolder> {
    public PainlessDeliveryAdapter(int layoutResId,
                                   @Nullable ArrayList<PainlessDeliveryInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PainlessDeliveryInfo item) {

    }
}
