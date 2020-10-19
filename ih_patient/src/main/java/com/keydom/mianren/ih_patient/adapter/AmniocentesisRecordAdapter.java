package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.AmniocentesisBean;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import java.util.List;

/**
 * @author 顿顿
 * @date 20/3/10 10:12
 * @des 羊水穿刺预约查询及取消
 */
public class AmniocentesisRecordAdapter extends BaseQuickAdapter<AmniocentesisBean,
        BaseViewHolder> {

    public AmniocentesisRecordAdapter(@Nullable List<AmniocentesisBean> data) {
        super(R.layout.item_amniocentesis_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AmniocentesisBean item) {
        helper.setText(R.id.item_amniocentesis_name, item.getName())
                .setText(R.id.item_amniocentesis_time, DateUtils.transDate(item.getCreateDate(),
                        DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.MM_DD_CH))
                .setText(R.id.item_amniocentesis_reserve_time,
                        DateUtils.transDate(item.getSurgeryTime(),
                                DateUtils.YYYY_MM_DD, DateUtils.MM_DD_CH))
                .addOnClickListener(R.id.item_amniocentesis_operate);
    }

}
