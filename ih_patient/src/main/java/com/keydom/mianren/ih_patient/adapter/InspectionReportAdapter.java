package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.inspection_report.BodyCheckDetailActivity;
import com.keydom.mianren.ih_patient.activity.inspection_report.InspectionDetailActivity;
import com.keydom.mianren.ih_patient.bean.InspectionRecordBean;
import com.keydom.mianren.ih_patient.constant.Type;

import java.util.List;

/**
 * 检验检查报告适配器
 *
 * @author 顿顿
 */
public class InspectionReportAdapter extends BaseQuickAdapter<InspectionRecordBean,
        BaseViewHolder> {
    /**
     * 类型 Type.INSPECTIONTYPE 检验  Type.BODYCHECKTYPE 检查
     */
    private int type;

    public InspectionReportAdapter(int type, @Nullable List<InspectionRecordBean> data) {
        super(R.layout.inspection_report_body_item, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectionRecordBean item) {
        helper.setText(R.id.body_tv, item.getItemName())
                .setText(R.id.body_date_tv, item.getPublishTime());
        helper.itemView.setOnClickListener(v -> {
            if (type == Type.INSPECTIONTYPE) {
                InspectionDetailActivity.start(mContext, item);
            } else {
                BodyCheckDetailActivity.start(mContext, item);
            }
        });
    }
}
