package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.PregnancyRecordItem;

import java.util.List;

/**
 * created date: 2019/1/15 on 16:44
 * des:VIP产检预约记录
 *
 * @author 顿顿
 */
public class PregnancyReserveAdapter extends BaseQuickAdapter<PregnancyRecordItem, BaseViewHolder> {
    public PregnancyReserveAdapter(@Nullable List<PregnancyRecordItem> data) {
        super(R.layout.item_pregnancy_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PregnancyRecordItem item) {
        helper.getView(R.id.item_pregnancy_record_is_finish_iv).setSelected(item.isFinsh());
        helper.setText(R.id.item_pregnancy_record_weeks_tv,
                StringUtils.isEmpty(item.getPreWeek()) ? "" : item.getPreWeek())
                .setText(R.id.item_pregnancy_record_projects_tv,
                        StringUtils.isEmpty(item.getProjectName()) ? "" : item.getProjectName())
                .setText(R.id.item_check_date_tv,
                        StringUtils.isEmpty(item.getPrenatalDate()) ? "" : item.getPrenatalDate())
                .setText(R.id.item_pregnancy_record_dates_tv,
                        StringUtils.isEmpty(item.getPrenatalDate()) ? "" : item.getPrenatalDate());
    }
}
