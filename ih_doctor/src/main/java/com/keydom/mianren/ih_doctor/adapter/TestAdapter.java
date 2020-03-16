package com.keydom.mianren.ih_doctor.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;

import java.util.List;

public class TestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TestAdapter(@Nullable List<String> data, String bean) {
        super(R.layout.drug_use_item_layout, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
    }

}
