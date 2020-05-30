package com.keydom.mianren.ih_doctor.activity.online_diagnose.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.CheckOutItemBean;

import java.util.List;

/**
 * @date 20/4/26 10:52
 * @des 检查项目  二级列表
 */
public class DiagnoseCheckItemAdapter extends BaseQuickAdapter<CheckOutItemBean, BaseViewHolder> {
    public DiagnoseCheckItemAdapter(@Nullable List<CheckOutItemBean> data) {
        super(R.layout.item_diagnose_sub, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckOutItemBean item) {
    }

}
