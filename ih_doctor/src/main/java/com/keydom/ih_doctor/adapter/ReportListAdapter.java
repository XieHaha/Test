package com.keydom.ih_doctor.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.inspection_report.BodyCheckDetailActivity;
import com.keydom.ih_doctor.activity.inspection_report.InspectionDetailActivity;
import com.keydom.ih_doctor.activity.online_diagnose.ReportListActivity;
import com.keydom.ih_doctor.bean.ReportItemBean;

import java.util.List;

public class ReportListAdapter extends BaseQuickAdapter<ReportItemBean, BaseViewHolder> {
    private int mType;

    public ReportListAdapter(@Nullable List<ReportItemBean> data, int type) {
        super(R.layout.report_item_layout, data);
        mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, ReportItemBean item) {

        helper.setText(R.id.report_name, item.getName());
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType == ReportListActivity.REPORT_INSPECT) {
                    BodyCheckDetailActivity.start(mContext, String.valueOf(item.getId()));
                } else if (mType == ReportListActivity.REPORT_CHECK_OUT) {
                    InspectionDetailActivity.start(mContext, String.valueOf(item.getId()));
                }
            }
        });

    }

}
