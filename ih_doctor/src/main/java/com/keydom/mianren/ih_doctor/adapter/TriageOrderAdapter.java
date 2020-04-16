package com.keydom.mianren.ih_doctor.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;

import java.util.ArrayList;

/**
 * @date 20/3/21 14:05
 * @des 分诊
 */
public class TriageOrderAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TriageOrderAdapter(@Nullable ArrayList<String> data) {
        super(R.layout.item_triage_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.triage_order_list_date_tv, "")
                .setText(R.id.triage_order_list_status_tv, "")
                .setText(R.id.triage_order_list_patient_info_tv, "")
                .setText(R.id.triage_order_list_description_tv, "")
                .setText(R.id.triage_order_list_apply_info_tv, "")
                .setText(R.id.triage_order_list_receive_info_tv, "");
    }
}
