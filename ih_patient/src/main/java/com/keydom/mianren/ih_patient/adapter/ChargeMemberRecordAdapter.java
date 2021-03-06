package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.RenewalRecordItem;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import java.util.List;

/**
 * created date: 2019/1/15 on 16:44
 * des:VIP缴费记录适配器
 */
public class ChargeMemberRecordAdapter extends BaseQuickAdapter<RenewalRecordItem, BaseViewHolder> {
    /**
     * 构建方法
     */
    public ChargeMemberRecordAdapter(@Nullable List<RenewalRecordItem> data) {
        super(R.layout.item_charge_member_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RenewalRecordItem item) {
        helper.setText(R.id.item_charge_member_record_date_tv,
                DateUtils.getDate(item.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS))
                .setText(R.id.item_charge_member_record_money_tv, item.getRenewalAmount() + "");
    }
}
