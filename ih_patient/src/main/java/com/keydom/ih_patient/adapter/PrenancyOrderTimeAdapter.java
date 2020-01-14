package com.keydom.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_patient.R;

import java.util.List;

/**
 * created date: 2019/1/15 on 16:44
 * des:产检预约时间
 */
public class PrenancyOrderTimeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    /**
     * 构建方法
     */
    public PrenancyOrderTimeAdapter(@Nullable List<String> data) {
        super(R.layout.item_pregnancy_order_time, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
/*        helper.setText(R.id.item_charge_member_record_date_tv, StringUtils.isEmpty(item.getCreateTime()) ? "" : item.getCreateTime())
                .setText(R.id.item_charge_member_record_money_tv, item.getRenewalAmount() + "");*/
    }
}
