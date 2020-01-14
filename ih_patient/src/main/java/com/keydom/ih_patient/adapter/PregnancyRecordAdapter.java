package com.keydom.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.RenewalRecordItem;

import java.util.List;

/**
 * created date: 2019/1/15 on 16:44
 * des:VIP产检预约记录
 */
public class PregnancyRecordAdapter extends BaseQuickAdapter<RenewalRecordItem, BaseViewHolder> {
    /**
     * 构建方法
     */
    public PregnancyRecordAdapter(@Nullable List<RenewalRecordItem> data) {
        super(R.layout.item_pregnancy_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RenewalRecordItem item) {
        /*helper.setText(R.id.item_charge_member_record_date_tv, StringUtils.isEmpty(item.getCreateTime()) ? "" : item.getCreateTime())
                .setText(R.id.item_charge_member_record_money_tv, item.getRenewalAmount() + "");*/
    }
}
