package com.keydom.ih_patient.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.CheckOutItemBean;

import java.util.List;
/**
 * 测试详情适配器
 */
public class TestDetailItemListAdapter extends BaseQuickAdapter<CheckOutItemBean, BaseViewHolder> {
    private Context context;
    /**
     * 构造方法
     */
    public TestDetailItemListAdapter(Context context, List<CheckOutItemBean> data) {
        super(R.layout.test_order_detail_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CheckOutItemBean item) {
        helper.setText(R.id.inspact_item_name, item.getName())
                .setText(R.id.test_item_fee, "￥" + item.getPrice() + "元");
    }
}
