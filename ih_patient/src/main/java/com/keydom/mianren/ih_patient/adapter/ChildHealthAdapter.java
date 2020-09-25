package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;

import java.util.List;

/**
 * 儿童保健
 *
 * @author 顿顿
 */
public class ChildHealthAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ChildHealthAdapter(@Nullable List<String> data) {
        super(R.layout.item_child_health, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String teamItem) {
        helper.setText(R.id.item_child_health_age_tv, "2020年2月27日")
                .setText(R.id.item_child_health_project_tv, "抽血检查");
    }
}
