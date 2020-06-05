package com.keydom.mianren.ih_patient.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.bean.CheckOutGroupBean;
import com.keydom.mianren.ih_patient.R;

import java.util.List;

/**
 * 测试详情适配器
 */
public class TestDetailItemListAdapter extends BaseQuickAdapter<CheckOutGroupBean, BaseViewHolder> {
    private Context context;

    /**
     * 构造方法
     */
    public TestDetailItemListAdapter(Context context, List<CheckOutGroupBean> data) {
        super(R.layout.test_order_detail_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CheckOutGroupBean item) {
        helper.setText(R.id.inspact_item_name, item.getInsCheckItemName())
                .setText(R.id.test_item_fee, "￥" + item.getPrice() + "元");
    }
}
