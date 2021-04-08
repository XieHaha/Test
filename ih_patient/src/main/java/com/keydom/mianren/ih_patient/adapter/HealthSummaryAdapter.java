package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.HealthSummaryBean;

import java.util.List;

/**
 * 干预方案
 *
 * @author 顿顿
 */
public class HealthSummaryAdapter extends BaseQuickAdapter<HealthSummaryBean,
        BaseViewHolder> {

    public HealthSummaryAdapter(@Nullable List<HealthSummaryBean> data) {
        super(R.layout.iten_health_summary, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HealthSummaryBean item) {
        helper.setText(R.id.item_health_summary_title_tv, item.getHealthConclusionName())
                .setText(R.id.item_health_summary_doctor_tv, item.getDoctorName())
                .setText(R.id.item_health_summary_content_tv, item.getCreateTime());
        TextView summaryStatus = helper.getView(R.id.item_health_summary_status_tv);
        String status = item.getHealthStatus();
        if (!TextUtils.isEmpty(status)) {
            if (status.contains("良好")) {
                summaryStatus.setBackgroundResource(R.drawable.health_summary_good_status);
            } else if (status.contains("一般")) {
                summaryStatus.setBackgroundResource(R.drawable.health_summary_normal_status);
            } else {
                summaryStatus.setBackgroundResource(R.drawable.health_summary_bad_status);
            }
        }

    }

}
