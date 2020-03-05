package com.keydom.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.PainlessDeliveryBean;

import java.util.ArrayList;

/**
 * @date 20/2/25 14:05
 * @des 无痛分娩预约适配器
 */
public class PainlessDeliveryAdapter extends BaseQuickAdapter<PainlessDeliveryBean,
        BaseViewHolder> {
    public PainlessDeliveryAdapter(int layoutResId,
                                   @Nullable ArrayList<PainlessDeliveryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PainlessDeliveryBean item) {
        helper.setText(R.id.tv_name, item.getPatientName())
                .setText(R.id.tv_due_date, item.getExpectedDateOfConfinement())
                .setText(R.id.tv_age, String.valueOf(item.getAge()))
                .setText(R.id.tv_fetus, String.valueOf(item.getEmbryoNumber()))
                .setText(R.id.tv_phone, item.getPhoneNumber())
                .addOnClickListener(R.id.tv_cancel);
    }
}
