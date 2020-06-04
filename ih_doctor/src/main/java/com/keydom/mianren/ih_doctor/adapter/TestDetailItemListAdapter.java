package com.keydom.mianren.ih_doctor.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.ih_common.bean.CheckOutGroupBean;

import java.util.List;

public class TestDetailItemListAdapter extends BaseQuickAdapter<CheckOutGroupBean, BaseViewHolder> {
    private Context context;

    public TestDetailItemListAdapter(Context context, List<CheckOutGroupBean> data) {
        super(R.layout.test_order_detail_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CheckOutGroupBean item) {
        helper.setText(R.id.inspact_item_name, item.getInsCheckCateName())
                .setText(R.id.test_item_fee, "￥" + item.getPrice() + "元");
    }
}
