package com.keydom.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_patient.R;

import java.util.List;

/**
 * @date 20/3/10 10:12
 * @des 羊水穿刺原因
 */
public class AmniocentesisReasonAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int curPosition = -1;

    public AmniocentesisReasonAdapter(@Nullable List<String> data) {
        super(R.layout.item_amniocentesis_reason, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.amniocentesis_reason_tv, item);
        LinearLayout layout = helper.getView(R.id.amniocentesis_reason_layout);
        layout.setSelected(curPosition == helper.getAdapterPosition());
    }

    public void setCurPosition(int curPosition) {
        if (this.curPosition == curPosition) {
            return;
        }
        this.curPosition = curPosition;
        notifyDataSetChanged();
    }
}
