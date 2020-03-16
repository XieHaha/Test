package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.PregnancyOrderTime;

import java.util.List;

/**
 * created date: 2019/1/15 on 16:44
 * des:产检预约时间
 */
public class PrenancyOrderTimeAdapter extends BaseQuickAdapter<PregnancyOrderTime, BaseViewHolder> {
    /**
     * 构建方法
     */
    public PrenancyOrderTimeAdapter(@Nullable List<PregnancyOrderTime> data) {
        super(R.layout.item_pregnancy_order_time, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PregnancyOrderTime item) {

        if(null != item){
            helper.getView(R.id.item_pregnancy_order_time_iv).setSelected(item.isSelected());
            helper.setText(R.id.item_pregnancy_order_time_tv, StringUtils.isEmpty(item.getTimeInterval()) ? "" : item.getTimeInterval());
        }
    }
}
