package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;

import java.util.List;

/**
 * 产科门诊次数
 */
public class OutpatientNumberAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private int curPosition = -1;

    public OutpatientNumberAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.obstetric_medical_day_which, item);
        RelativeLayout layout = helper.getView(R.id.obstetric_medical_day_layout);
        layout.setSelected(curPosition == helper.getAdapterPosition());
    }

    public void setCurPosition(int curPosition) {
        this.curPosition = curPosition;
        notifyDataSetChanged();
    }
}
