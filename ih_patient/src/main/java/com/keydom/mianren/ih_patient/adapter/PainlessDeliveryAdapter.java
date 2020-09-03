package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.PainlessDeliveryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 顿顿
 * @date 20/2/25 14:05
 * @des 无痛分娩预约适配器
 */
public class PainlessDeliveryAdapter extends BaseQuickAdapter<PainlessDeliveryBean,
        BaseViewHolder> {
    /**
     * 胎数
     */
    List<String> fetusDit = new ArrayList<>();

    {
        fetusDit.add("单胎");
        fetusDit.add("双胎");
        fetusDit.add("多胎");
    }

    public PainlessDeliveryAdapter(int layoutResId,
                                   @Nullable ArrayList<PainlessDeliveryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PainlessDeliveryBean item) {
        helper.setText(R.id.tv_name, item.getPatientName())
                .setText(R.id.tv_due_date, item.getExpectedDateOfConfinement())
                .setText(R.id.tv_age, item.getAge())
                .setText(R.id.tv_fetus, fetusDit.get(item.getEmbryoNumber()))
                .setText(R.id.tv_phone, item.getPhoneNumber())
                .addOnClickListener(R.id.tv_cancel);
    }
}
