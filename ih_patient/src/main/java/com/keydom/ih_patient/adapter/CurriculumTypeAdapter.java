package com.keydom.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_patient.R;

import java.util.List;

/**
 * 课程类别
 */
public class CurriculumTypeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CurriculumTypeAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String teamItem) {
        helper.setText(R.id.tv_type, teamItem);
    }
}
