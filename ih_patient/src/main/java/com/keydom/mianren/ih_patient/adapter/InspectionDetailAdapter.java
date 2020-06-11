package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.CheckoutResultListBean;

import java.util.List;

/**
 * 检查详情适配器
 */
public class InspectionDetailAdapter extends BaseQuickAdapter<CheckoutResultListBean,
        BaseViewHolder> {
    /**
     * 构造方法
     */
    public InspectionDetailAdapter(@Nullable List<CheckoutResultListBean> checkoutResultList) {
        super(R.layout.inspection_data_item, checkoutResultList);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckoutResultListBean item) {
        helper.setText(R.id.project_name_tv, item.getItemDetailsName())
                .setText(R.id.project_result_tv, item.getResultValue());
    }
}
