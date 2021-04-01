package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.PatientSurgeryHistoryBean;

import java.util.List;

/**
 * 手术史
 *
 * @author 顿顿
 */
public class SurgeryHistoryAdapter extends BaseQuickAdapter<PatientSurgeryHistoryBean,
        BaseViewHolder> {

    public SurgeryHistoryAdapter(@Nullable List<PatientSurgeryHistoryBean> data) {
        super(R.layout.item_health_add_surgery, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatientSurgeryHistoryBean item) {
        helper.setText(R.id.item_surgery_history_name_tv, item.getName())
                .setText(R.id.item_surgery_history_date_tv, item.getSurgeryDate());
        TextView status = helper.getView(R.id.item_surgery_history_status_tv);
        status.setCompoundDrawables(null, null, null, null);
        status.setText(item.getSurgeryStatus());
    }
}
