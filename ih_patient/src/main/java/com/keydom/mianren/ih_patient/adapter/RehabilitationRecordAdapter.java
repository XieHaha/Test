package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;

import java.util.List;

/**
 * 产后康复适配器
 */
public class RehabilitationRecordAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public RehabilitationRecordAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String teamItem) {
        helper.setText(R.id.tv_title, "测试标题")
                .setText(R.id.tv_content, "测试内容")
                .setText(R.id.tv_time, "10分钟前")
                .setText(R.id.tv_comment, "10")
                .setText(R.id.tv_accolade, "10")
                .addOnClickListener(R.id.iv_follow)
                .addOnClickListener(R.id.tv_comment)
                .addOnClickListener(R.id.tv_accolade);
    }
}
